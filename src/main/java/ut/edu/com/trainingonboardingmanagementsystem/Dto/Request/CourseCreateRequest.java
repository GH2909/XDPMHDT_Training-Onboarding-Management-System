package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.CourseCategory;

@Setter
@Getter
public class CourseCreateRequest {
    private String courseName;

    private Integer duration;

    private String description;

    private CourseCategory category;

    private String completionRule;
}
