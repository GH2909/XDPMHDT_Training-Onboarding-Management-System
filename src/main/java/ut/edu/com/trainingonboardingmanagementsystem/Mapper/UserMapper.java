package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.EmployeeProfileResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public EmployeeProfileResponse getEmployeeProfile(User employee) {

        return EmployeeProfileResponse.builder()
                .id(employee.getId())
                .userName(employee.getUserName())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .phone(employee.getPhone())
                .avatar(employee.getAvatar())
                .status(employee.getStatus())
                .roleName(employee.getRole() != null ? employee.getRole().getRoleName() : null)
                .createdAt(employee.getCreatedAt())
                .build();
    }
    public void updateEmployeeProfile(User employee, EmployeeProfileUpdateRequest request) {
        employee.setFullName(request.getFullName());
        employee.setPhone(request.getPhone());
        if (request.getAvatar() != null) {
            employee.setAvatar(request.getAvatar());
        }
    }
    }

