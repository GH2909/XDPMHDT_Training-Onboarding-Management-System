package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;

@Component
public class LessonMapper {
    public Lesson CreateLesson (LessonRequest request, Course course, User create) {
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setCreatedBy(create);
        lesson.setTitle(request.getTitle());
        lesson.setDuration(request.getDuration());
        lesson.setDescription(request.getDescription());
        return lesson;
    }

    public LessonResponse lessonResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .courseName(lesson.getCourse().getCourseName())
                .title(lesson.getTitle())
                .duration(lesson.getDuration())
                .description(lesson.getDescription())
                .createdBy(lesson.getCreatedBy().getEmail())
                .build();
    }

    public void updateLesson(Lesson lesson, LessonRequest request) {
        lesson.setTitle(request.getTitle());
        lesson.setDuration(request.getDuration());
        lesson.setDescription(request.getDescription());
    }
}
