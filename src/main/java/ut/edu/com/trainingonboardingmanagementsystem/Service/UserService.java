package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChangePasswordRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.EmployeeProfileResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.UserMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.EmployeeValidator;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmployeeValidator employeeValidator;

    public EmployeeProfileResponse getProfile(String email) {

        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên."));

        return userMapper.getEmployeeProfile(employee);
    }

    public EmployeeProfileResponse updateEmployeeProfile(String email, EmployeeProfileUpdateRequest request) {
        employeeValidator.validateProfileUpdate(email, request);
        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên."));

        userMapper.updateEmployeeProfile(employee, request);
        User updatedEmployee = userRepository.save(employee);

        return userMapper.getEmployeeProfile(updatedEmployee);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        employeeValidator.validateChangePassword(email,request);
        User employee = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        employee.setPassword(request.getNewPassword());
        userRepository.save(employee);
    }

    public List<User> getEmployeeProfiles() {
        return userRepository.findByRoleName("EMPLOYEE");
    }
    public User getEmployeeProfile(String email) {
        return userRepository.findByEmail(email) .orElseThrow(() -> new RuntimeException("Employee Profile not found"));
    }

}
