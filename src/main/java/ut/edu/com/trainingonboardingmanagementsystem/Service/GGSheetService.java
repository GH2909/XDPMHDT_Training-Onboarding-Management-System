package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;

import java.util.List;

@Service
@RequiredArgsConstructor

public class GGSheetService {
    @Value("${gg.sheet.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public List<UserFromGGSheet> readUsers() {
        UserFromGGSheet[] users =
                restTemplate.getForObject(apiUrl, UserFromGGSheet[].class);

        System.out.println("GGSheet users = " + (users == null ? 0 : users.length));

        return users == null ? List.of() : List.of(users);
    }
}
