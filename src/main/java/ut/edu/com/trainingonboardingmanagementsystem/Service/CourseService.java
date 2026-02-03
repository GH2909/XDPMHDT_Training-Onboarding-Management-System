package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.CourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.CourseMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepo;
    @Autowired
    private CourseMapper courseMapper;

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

    public List<CourseResponse> getAll() {
        return courseRepo.findAll()
                .stream()
                .map(course -> courseMapper.toCourseResponse(course))
                .toList();
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
