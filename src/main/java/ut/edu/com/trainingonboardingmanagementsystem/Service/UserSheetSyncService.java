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

            if (dto.getUsername() == null || dto.getRoleName() == null) continue;

            Role role = roleRepository.findByRoleName(dto.getRoleName())
                    .orElseGet(() ->
                            roleRepository.save(
                                    Role.builder()
                                            .roleName(dto.getRoleName())
                                            .build()
                            )
                    );

            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseGet(User::new);

            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setEmail(dto.getEmail());
            user.setFullName(dto.getFullname());
            user.setPhone(dto.getPhone());
            user.setAvatar(dto.getAvatar());
            user.setRole(role);

            if (dto.getStatus() != null) {
                user.setStatus(UserStatus.valueOf(dto.getStatus().toUpperCase()));
            }

            userRepository.save(user);
        }
    }
}