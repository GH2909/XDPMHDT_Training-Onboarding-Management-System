package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResultResponse {
    private Integer id;
    private String content;
    private String choiceLabel;
    private Boolean isCorrect;
    private Boolean wasSelected;
}
