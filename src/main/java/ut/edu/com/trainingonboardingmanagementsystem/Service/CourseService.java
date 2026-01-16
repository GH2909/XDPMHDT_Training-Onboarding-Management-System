package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepo;

    @Transactional
    public Course create(CourseCreateRequest req) {
        if (req.getCourseName() == null || req.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khóa học không được để trống");
        }

        Course c = Course.builder()
                .courseName(req.getCourseName())
                .duration(req.getDuration())
                .description(req.getDescription())
                .category(req.getCategory() != null ? req.getCategory() : ut.edu.com.trainingonboardingmanagementsystem.enums.CourseCategory.Onboarding)
                .completionRule(req.getCompletionRule())
                .build();
        return courseRepo.save(c);
    }

    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    public Course getById(Integer id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại với ID: " + id));
    }

    @Transactional
    public Course update(Integer id, CourseUpdateRequest req) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại với ID: " + id));

        if (req.getCourseName() != null && !req.getCourseName().trim().isEmpty()) {
            course.setCourseName(req.getCourseName());
        }
        if (req.getDuration() != null) {
            course.setDuration(req.getDuration());
        }
        if (req.getDescription() != null) {
            course.setDescription(req.getDescription());
        }
        if (req.getCategory() != null) {
            course.setCategory(req.getCategory());
        }
        if (req.getCompletionRule() != null) {
            course.setCompletionRule(req.getCompletionRule());
        }

        return courseRepo.save(course);
    }

    @Transactional
    public void delete(Integer id) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại với ID: " + id));
        courseRepo.delete(course);
    }
}
