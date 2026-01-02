//package ut.edu.com.trainingonboardingmanagementsystem.Mapper;
//
//import org.springframework.stereotype.Component;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonCreateRequest;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
//
//@Component
//public class LessonMapper {
//    public Lesson CreateLesson (LessonCreateRequest request, Course course, User user) {
//        Lesson les = new Lesson();
//        les.setCourse(course);
//        les.setCreatedBy(user);
//        les.setTitle(request.getTitle());
//        les.setDuration(request.getDuration());
//        les.setDescription(request.getDescription());
//        return les;
//    }
//
//    public LessonResponse lessonResponse (Lesson lesson) {
//        LessonResponse lessonRes = new LessonResponse();
//        lessonRes.setId(lesson.getId());
//        lessonRes.setDuration(lesson.getDuration());
//        lessonRes.setTitle(lesson.getTitle());
//        lessonRes.setDescription(lesson.getDescription());
//        return lessonRes;
//    }
//}
