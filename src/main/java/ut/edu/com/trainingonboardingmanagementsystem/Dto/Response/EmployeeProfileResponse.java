package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileResponse {
    private Integer id;
    private String userName;
    private String email;
    private String fullName;
    private String phone;
    private String avatar;
    private UserStatus status;
    private String roleName;
    private LocalDateTime createdAt;
    private EmployeeStatisticsResponse statistics;
}

