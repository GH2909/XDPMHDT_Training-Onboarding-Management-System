package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignCourseRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.AssignmentRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository repo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;

    @Transactional
    public void assign(AssignCourseRequest req, Integer hrId) {

        Course course = courseRepo.findById(req.getCourseId())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));

        for (Integer empId : req.getEmployeeIds()) {

            if (repo.existsByEmployeeIdAndCourseId(empId, course.getId())) {
                throw new RuntimeException("Nhân viên đã được gán khóa học");
            }

            Assignment a = new Assignment();
            a.setCourse(course);
            a.setEmployee(userRepo.findById(empId).orElseThrow());
            a.setAssignedBy(userRepo.findById(hrId).orElseThrow());
            a.setAssignedDate(LocalDate.now());

            repo.save(a);
        }
    }
}
