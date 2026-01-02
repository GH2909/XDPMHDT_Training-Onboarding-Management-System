package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionRequest {
    private Integer quizId;
    private Integer questionId;
    private Integer sequenceNumber;
}
