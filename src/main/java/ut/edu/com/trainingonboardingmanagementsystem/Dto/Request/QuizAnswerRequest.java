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
public class QuizAnswerRequest {

    @NotNull
    private Integer questionId;

    @NotEmpty
    private List<Integer> choiceIds;
}
