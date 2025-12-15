package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.UserMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EmployeeProfileService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public User updateEmployeeProfile(String email, EmployeeProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateEmployeeProfile(user, request);
        return userRepository.save(user);
    }
}
