package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.LessonMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonResponse createLesson(LessonRequest request, Course course, User user) {
        Lesson lesson = lessonMapper.CreateLesson(request, course, user);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.lessonResponse(savedLesson);
    }

    public LessonResponse updateLesson(Integer id, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        lessonMapper.updateEntity(lesson, request);
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
