package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepo;
    private final CourseRepository courseRepo;

    public Lesson createLesson(LessonCreateRequest request, User user) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ValidationException("Vui lòng nhập tên bài giảng");
        }

        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Khóa học không tồn tại"));

        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setDuration(request.getDuration());
        lesson.setCourse(course);
        lesson.setCreatedBy(user);

        return lessonRepo.save(lesson);
    }
}
