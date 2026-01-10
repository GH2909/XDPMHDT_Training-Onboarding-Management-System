package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultResponse {
    private Integer quizId;
    private Integer userId;
    private Integer totalScore;
    private Integer maxScore;
    private Integer passScore;
    private Boolean passed;
    private Double percentage;
    private Integer correctAnswers;
    private Integer totalQuestions;
    private LocalDateTime submittedAt;
    private List<QuestionResultResponse> questionResults;
}
