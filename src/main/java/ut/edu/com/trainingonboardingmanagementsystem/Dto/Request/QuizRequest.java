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

    @NotNull
    private Integer lessonId;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;

    @NotNull
    private Integer categoryId;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotNull
    @Positive
    private Integer maxScore;

    @NotNull
    @Positive
    private Integer passScore;

    @NotNull
    @Positive
    private Integer attemptLimit;
    private QuizStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean showAnswers;
    private Boolean allowReview;
    private Boolean shuffleQuestions;
    private Boolean shuffleChoices;
}
