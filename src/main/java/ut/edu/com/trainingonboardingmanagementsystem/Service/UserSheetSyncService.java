package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Role;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.RoleRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

@Service
@RequiredArgsConstructor
public class UserSheetSyncService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void upsertUser(UserFromGGSheet dto) {

        Role role = roleRepository.findByRoleName(dto.getRoleName())
                .orElseThrow(() ->
                        new RuntimeException("Role không tồn tại: " + dto.getRoleName())
                );

        User user = userRepository.findByUsername(dto.getUsername())
                .orElse(new User());

        user.setUserName(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullname());
        user.setPhone(dto.getPhone());
        user.setAvatar(dto.getAvatar());
        user.setStatus(UserStatus.valueOf(dto.getStatus()));
        user.setRole(role);

        userRepository.save(user);
    }
}