package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.CourseCategory;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseForEmployeeResponse {
    private Integer id;
    private String courseName;
    private String description;
    private CourseCategory category;
    private Integer duration;
    private String completionRule;
    private List<LessonForEmployeeResponse> lessons;
    private List<ModuleResponse> modules;
    private LearningProgressResponse learningProgress;
}
