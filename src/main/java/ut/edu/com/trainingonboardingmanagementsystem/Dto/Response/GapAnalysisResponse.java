package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GapAnalysisResponse {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private Integer competencyId;
    private String competencyName;
    private Integer requiredLevel;
    private Integer currentLevel;
    private Integer gapValue;
}
