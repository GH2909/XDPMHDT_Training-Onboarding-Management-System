package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionResponse {
    private Integer quizId;
    private Integer questionId;
    private String questionContent;
    private Integer sequenceNumber;
}
