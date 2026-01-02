package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Integer id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer maxScore;
    private QuizStatus status;
}
