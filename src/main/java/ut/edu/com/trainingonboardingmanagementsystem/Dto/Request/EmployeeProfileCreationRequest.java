package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileCreationRequest {
    private String password;
    private String fullName;
    private String phone;
    private String avatar;
}
