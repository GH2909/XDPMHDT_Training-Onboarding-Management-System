package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.Getter;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.ModuleType;

@Setter
@Getter
public class ModuleRequest {
    private ModuleType type;
    private String url;
    private Integer courseId;
}
