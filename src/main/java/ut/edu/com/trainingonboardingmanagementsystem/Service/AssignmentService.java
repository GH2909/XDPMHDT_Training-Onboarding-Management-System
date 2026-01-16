package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignCourseRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.AssignmentRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository repo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;

    @Transactional
    public Assignment assign(AssignCourseRequest req, Integer hrId) {
        if (req.getCourseId() == null) {
            throw new IllegalArgumentException("Vui lòng chọn khóa học");
        }

        if (req.getEmployeeIds() == null || req.getEmployeeIds().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn người nhận khóa học");
        }

        Course course = courseRepo.findById(req.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại với ID: " + req.getCourseId()));

        User hr = userRepo.findById(hrId)
                .orElseThrow(() -> new ResourceNotFoundException("Người gán không tồn tại với ID: " + hrId));

        // Check for duplicates first
        for (Integer empId : req.getEmployeeIds()) {
            if (repo.existsByEmployeeIdAndCourseId(empId, course.getId())) {
                User employee = userRepo.findById(empId)
                        .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với ID: " + empId));
                throw new DuplicateResourceException("Nhân viên " + employee.getFullName() + " đã được gán khóa học này");
            }
        }

        // Create assignments
        Assignment firstAssignment = null;
        for (Integer empId : req.getEmployeeIds()) {
            User employee = userRepo.findById(empId)
                    .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với ID: " + empId));

            Assignment assignment = Assignment.builder()
                    .course(course)
                    .employee(employee)
                    .assignedBy(hr)
                    .assignedDate(LocalDate.now())
                    .build();

            Assignment saved = repo.save(assignment);
            if (firstAssignment == null) {
                firstAssignment = saved;
            }
        }

        return firstAssignment;
    }

    public List<Assignment> getAll() {
        return repo.findAll();
    }

    public Assignment getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phân công không tồn tại với ID: " + id));
    }

    public List<Assignment> getByEmployeeId(Integer employeeId) {
        return repo.findByEmployeeIdWithCourse(employeeId);
    }

    @Transactional
    public void delete(Integer id) {
        Assignment assignment = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phân công không tồn tại với ID: " + id));
        repo.delete(assignment);
    }

    @Transactional
    public void deleteByEmployeeAndCourse(Integer employeeId, Integer courseId) {
        Assignment assignment = repo.findByEmployeeIdAndCourseId(employeeId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Phân công không tồn tại"));
        repo.delete(assignment);
    }
}
