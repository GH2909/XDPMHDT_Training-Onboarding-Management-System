package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserFromGGSheet {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String phone;
    private String avatar;
    private String status;
    private String roleName;
}
