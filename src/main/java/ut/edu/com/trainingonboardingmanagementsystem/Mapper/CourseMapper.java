package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.CourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;

@Component
public class CourseMapper {
    public CourseResponse toCourseResponse(Course course) {
        CourseResponse dto = new CourseResponse();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setDuration(course.getDuration());
        dto.setCategory(course.getCategory());
        dto.setDescription(course.getDescription());

        return dto;
    }

}
