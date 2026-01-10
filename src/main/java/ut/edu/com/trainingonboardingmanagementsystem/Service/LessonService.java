package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.LessonMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    public LessonResponse createLesson(LessonRequest request) {

        Course course = courseRepository.findById(request.getCourse())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id: " + request.getCourse()
                        )
                );

        User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id: " + request.getCreatedBy()
                        )
                );
        Lesson lesson = lessonMapper.CreateLesson(request, course, user);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.lessonResponse(savedLesson);
    }

    public LessonResponse updateLesson(Integer id, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        lessonMapper.updateLesson(lesson, request);
        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.lessonResponse(updatedLesson);
    }

    public LessonResponse getLessonById(Integer id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.lessonResponse(lesson);
    }

    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::lessonResponse)
                .collect(Collectors.toList());
    }

    public void deleteLesson(Integer id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }
}
