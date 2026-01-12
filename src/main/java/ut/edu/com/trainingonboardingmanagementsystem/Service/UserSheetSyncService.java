package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Role;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.RoleRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSheetSyncService {

    private final GGSheetService ggSheetService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void syncUsersFromGGSheet() {

        List<UserFromGGSheet> sheetUsers = ggSheetService.readUsers();

        System.out.println("Sheet users size = " + sheetUsers.size());

        for (UserFromGGSheet dto : sheetUsers) {

            if (dto.getUsername() == null || dto.getRoleName() == null) {
                System.out.println("Skip row: " + dto);
                continue;
            }

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

            user.setUserName(dto.getUsername());

            if (dto.getPassword() != null) {
                user.setPassword(dto.getPassword());
            }

            user.setEmail(dto.getEmail());
            user.setFullName(dto.getFullname());
            user.setPhone(dto.getPhone());
            user.setAvatar(dto.getAvatar());
            user.setRole(role);

            try {
                if (dto.getStatus() != null) {
                    user.setStatus(
                            UserStatus.valueOf(dto.getStatus().trim().toUpperCase())
                    );
                }
            } catch (Exception e) {
                System.out.println("Invalid status: " + dto.getStatus());
            }

            userRepository.save(user);
        }
    }
}
