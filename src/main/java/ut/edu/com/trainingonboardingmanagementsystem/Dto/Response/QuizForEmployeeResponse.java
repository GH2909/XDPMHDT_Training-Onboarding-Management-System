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
public class QuizForEmployeeResponse {
    private Integer id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer maxScore;
    private Integer passScore;
    private Integer attemptLimit;
    private QuizStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean allowReview;
    private Integer totalQuestions;
    private QuizAttemptSummaryResponse bestAttempt;
    private Integer remainingAttempts;
}
