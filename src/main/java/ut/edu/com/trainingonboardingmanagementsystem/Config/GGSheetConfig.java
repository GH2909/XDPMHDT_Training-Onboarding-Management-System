package ut.edu.com.trainingonboardingmanagementsystem.Config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
public class GGSheetConfig {
    @Bean
    public Sheets sheetsService() throws Exception {

        GoogleCredentials credentials =
                GoogleCredentials.fromStream(
                        new ClassPathResource("credentials.json").getInputStream()
                ).createScoped(List.of(SheetsScopes.SPREADSHEETS_READONLY));

        HttpRequestInitializer requestInitializer =
                new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer
        )
                .setApplicationName("Spring Boot Sheets")
                .build();
    }
}
