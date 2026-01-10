package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "Content is required")
    @Size(max = 255, message = "Content must not exceed 255 characters")
    private String content;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    @NotEmpty(message = "At least one choice is required")
    @Valid
    private List<ChoiceRequest> choices;
}
