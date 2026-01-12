package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningProgressResponse {
    private Integer id;
    private Float progressPercent;
    private Float score;
    private LearningStatus status;
    private Integer completedLessons;
    private Integer totalLessons;
    private Integer completedQuizzes;
    private Integer totalQuizzes;
}
