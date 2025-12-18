package ut.edu.com.trainingonboardingmanagementsystem.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gg.sheet")
@Getter
@Setter
public class GGSheetProperties {
    private String id;
    private String range;
}
