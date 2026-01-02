package ut.edu.com.trainingonboardingmanagementsystem.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFromGGSheet {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("status")
    private String status;

    @JsonProperty("roleName")
    private String roleName;
}
