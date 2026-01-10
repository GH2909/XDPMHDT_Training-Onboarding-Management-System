package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {

    @NotNull(message = "Lesson ID is required")
    private Integer lessonId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationMinutes;

    @NotNull(message = "Max score is required")
    @Positive(message = "Max score must be positive")
    private Integer maxScore;

    @NotNull(message = "Pass score is required")
    @Positive(message = "Pass score must be positive")
    private Integer passScore;

    @NotNull(message = "Attempt limit is required")
    @Positive(message = "Attempt limit must be positive")
    private Integer attemptLimit;
    private QuizStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean showAnswers;
    private Boolean allowReview;
    private Boolean shuffleQuestions;
    private Boolean shuffleChoices;
}
