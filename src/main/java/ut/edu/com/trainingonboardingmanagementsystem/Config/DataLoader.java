//package ut.edu.com.trainingonboardingmanagementsystem.Config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.Role;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
//import ut.edu.com.trainingonboardingmanagementsystem.Repository.RoleRepository;
//import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader {
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void init() {
//        User admin = userRepository.findByUsername("huonggiang")
//                .orElse(new User());
//
//        List<Role> roles = roleRepository.findAllByRoleName("ADMIN");
//        if (roles.isEmpty()) {
//            throw new RuntimeException("Role ADMIN chưa tồn tại trong database!");
//        }
//        Role r = roles.get(0);
//
//        admin.setUsername("huonggiang");
//        admin.setPassword(passwordEncoder.encode("123456"));
//        admin.setRole(r);
//        userRepository.save(admin);
//    }
//}
