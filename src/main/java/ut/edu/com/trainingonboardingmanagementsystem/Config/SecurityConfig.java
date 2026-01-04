package ut.edu.com.trainingonboardingmanagementsystem.Config;
//package ut.edu.com.trainingonboardingmanagementsystem.;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ut.edu.com.trainingonboardingmanagementsystem.Security.Jwt.JwtAuthFilter;
import ut.edu.com.trainingonboardingmanagementsystem.Security.User.CusDetailsService;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final CusDetailsService cusDetailsService;
    @Autowired
    private final JwtAuthFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Tắt CSRF vì dùng JWT
                .csrf(csrf -> csrf.disable())

                // Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Cấu hình phân quyền
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()   // login, register
                        .requestMatchers("/hr/**").hasRole("HR")   // chỉ HR
                        .anyRequest().authenticated()              // còn lại phải đăng nhập
                )

                // Custom UserDetailsService
                .userDetailsService(cusDetailsService)

                // JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
