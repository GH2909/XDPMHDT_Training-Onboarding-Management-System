package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateRequest {
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
