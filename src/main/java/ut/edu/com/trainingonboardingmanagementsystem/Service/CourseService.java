package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepo;

    public Course create(CourseCreateRequest req) {

        if (courseRepo.existsByCourseName(req.getCourseName())){
            throw new RuntimeException("Khóa học đã tồn tại");
        }
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

    public Course update(Integer id, CourseUpdateRequest req){
        Course c = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với id: "+ id));

        c.setCourseName(req.getCourseName());
        c.setDuration(req.getDuration());
        c.setDescription(req.getDescription());
        c.setCategory(req.getCategory());
        c.setCompletionRule(req.getCompletionRule());
        return courseRepo.save(c);
    }

    public void delete(Integer id){
        if (!courseRepo.existsById(id)){
            throw new RuntimeException("Khóa học không tồn tại");
        }
        courseRepo.deleteById(id);
    }
}
