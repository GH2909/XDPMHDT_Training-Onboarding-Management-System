package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultResponse {
    private Integer questionId;
    private String questionContent;
    private List<Integer> selectedChoiceIds;
    private List<Integer> correctChoiceIds;
    private Integer earnedScore;
    private Integer maxScore;
    private Boolean correct;
}
