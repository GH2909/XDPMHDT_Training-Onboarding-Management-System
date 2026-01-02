package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateRequest {
    private String content;
    private QuestionType type;
    private Integer quizId;
    private Integer sequenceNumber;
}
