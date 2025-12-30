package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepo;

    public Course create(CourseCreateRequest req) {
        Course c = new Course();
        c.setCourseName(req.getCourseName());
        c.setDuration(req.getDuration());
        c.setDescription(req.getDescription());
        c.setCategory(req.getCategory());
        c.setCompletionRule(req.getCompletionRule());
        return courseRepo.save(c);
    }

    public List<Course> getAll() {
        return courseRepo.findAll();
    }
}
