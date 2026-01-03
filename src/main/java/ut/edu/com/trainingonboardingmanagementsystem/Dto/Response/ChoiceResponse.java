package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResponse {
    private Integer id;
    private String content;
    private String choices;
    private Boolean isAnswer; // Chỉ hiển thị khi showAnswers = true
    private Integer score;
    private Integer orderIndex;
}
