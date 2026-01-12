package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.ModuleType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private Integer id;
    private ModuleType type;
    private String url;
}
