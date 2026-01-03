package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {
    private Integer id;
    private Integer lessonId;
    private String title;
    private String description;
    private Integer categoryId;
    private Integer durationMinutes;
    private Integer maxScore;
    private Integer passScore;
    private Integer attemptLimit;
    private QuizStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean showAnswers;
    private Boolean allowReview;
    private Boolean shuffleQuestions;
    private Boolean shuffleChoices;
    private LocalDateTime updatedAt;
}
