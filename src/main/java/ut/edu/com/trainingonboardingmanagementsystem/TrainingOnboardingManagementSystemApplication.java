package ut.edu.com.trainingonboardingmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "ut.edu.com.trainingonboardingmanagementsystem")
public class TrainingOnboardingManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingOnboardingManagementSystemApplication.class, args);
    }

}
