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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSheetSyncService {
    private final GGSheetService ggSheetService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void syncUsersFromGGSheet() {

        List<UserFromGGSheet> sheetUsers = ggSheetService.readUsers();

        for (UserFromGGSheet dto : sheetUsers) {

            if (userRepository.existsByUsername(dto.getUsername())) {
                continue;
            }

            Role role = roleRepository
                    .findByRoleName(dto.getRoleName())
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .roleName(dto.getRoleName())
                                    .build()
                    ));

            User user = User.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .email(dto.getEmail())
                    .fullName(dto.getFullname())
                    .phone(dto.getPhone())
                    .avatar(dto.getAvatar())
                    .status(UserStatus.valueOf(dto.getStatus().toUpperCase()))
                    .role(role)
                    .build();

            userRepository.save(user);
        }
    }
}