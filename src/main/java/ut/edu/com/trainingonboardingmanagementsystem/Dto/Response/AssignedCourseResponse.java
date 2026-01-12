package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.CourseCategory;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedCourseResponse {
    private Integer courseId;
    private String courseName;
    private String description;
    private CourseCategory category;
    private Integer duration;
    private LocalDate assignedDate;
    private String assignedByName;
    private LearningProgressResponse learningProgress;
}
