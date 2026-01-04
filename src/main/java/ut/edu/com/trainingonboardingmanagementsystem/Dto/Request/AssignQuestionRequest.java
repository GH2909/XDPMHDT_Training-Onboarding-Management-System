package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignQuestionRequest {
    private Integer questionId;
    private Integer sequenceNumber;
}
