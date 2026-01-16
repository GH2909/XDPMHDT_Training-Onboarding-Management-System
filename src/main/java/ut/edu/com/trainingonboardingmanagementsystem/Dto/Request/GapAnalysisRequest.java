package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GapAnalysisRequest {
    private Integer employeeId;
    private Integer competencyId;
    private Integer requiredLevel;
    private Integer currentLevel;
}
