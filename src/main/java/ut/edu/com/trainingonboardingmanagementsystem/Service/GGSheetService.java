package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ut.edu.com.trainingonboardingmanagementsystem.Model.UserFromGGSheet;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GGSheetService {

    @Value("${gg.sheet.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public List<UserFromGGSheet> readUsers() {

        try {
            ResponseEntity<UserFromGGSheet[]> response =
                    restTemplate.getForEntity(apiUrl, UserFromGGSheet[].class);

            if (!response.getStatusCode().is2xxSuccessful()
                    || response.getBody() == null) {

                log.warn("GGSheet response invalid: status={}",
                        response.getStatusCode());
                return Collections.emptyList();
            }

            log.info("GGSheet users loaded: {}", response.getBody().length);
            return List.of(response.getBody());

        } catch (ResourceAccessException ex) {
            // Timeout / connect failed
            log.error("Cannot connect to GGSheet API (timeout): {}", ex.getMessage());
            return Collections.emptyList();

        } catch (Exception ex) {
            log.error("Error while reading GGSheet", ex);
            return Collections.emptyList();
        }
    }
}
