package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
