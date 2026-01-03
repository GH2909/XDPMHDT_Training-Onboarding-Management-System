package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.*;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateRequest {
    private String content;
    private QuestionType type;
    private List<ChoiceRequest> choices;
}
