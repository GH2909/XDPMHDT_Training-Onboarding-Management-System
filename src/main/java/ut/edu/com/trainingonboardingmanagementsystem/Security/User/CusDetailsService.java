package ut.edu.com.trainingonboardingmanagementsystem.Security.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

@Service
@RequiredArgsConstructor
public class CusDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if (user.getStatus() == UserStatus.LOCKED) {
            throw new RuntimeException("User is locked");
        }
        System.out.println("Login username = " + username);


        return new UserPrincipal(user);
    }
}
