package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptSummaryResponse {
    private Integer attemptNumber;
    private Integer score;
    private Integer maxScore;
    private Boolean passed;
    private Float percentage;
    private LocalDateTime submittedAt;
}
