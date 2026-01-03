package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;

@Component
public class LessonMapper {
    public Lesson CreateLesson (LessonRequest request, Course course, User user) {
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setCreatedBy(user);
        lesson.setTitle(request.getTitle());
        lesson.setDuration(request.getDuration());
        lesson.setDescription(request.getDescription());
        return lesson;
    }

    public LessonResponse lessonResponse (Lesson lesson) {
        LessonResponse lessonRes = new LessonResponse();
        lessonRes.setId(lesson.getId());
        lessonRes.setCourseId(lesson.getCourse().getId());
        lessonRes.setDuration(lesson.getDuration());
        lessonRes.setTitle(lesson.getTitle());
        lessonRes.setDescription(lesson.getDescription());
        return lessonRes;
    }

    public void updateEntity(Lesson lesson, LessonRequest request) {
        lesson.setTitle(request.getTitle());
        lesson.setDuration(request.getDuration());
        lesson.setDescription(request.getDescription());
    }
}
