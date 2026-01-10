package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitQuizRequest {
    @NotNull(message = "Quiz ID is required")
    private Integer quizId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotEmpty(message = "Answers are required")
    private List<AnswerRequest> answers;
}
