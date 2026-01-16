package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.Getter;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.CourseCategory;

@Setter
@Getter
public class CourseUpdateRequest {
    private String courseName;
    private Integer duration;
    private String description;
    private CourseCategory category;
    private String completionRule;
}
