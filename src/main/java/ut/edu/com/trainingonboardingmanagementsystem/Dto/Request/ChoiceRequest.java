package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {
    private String content;
    private String choices;
    private Boolean isAnswer;

    private Integer score;
    private Integer orderIndex;
}
