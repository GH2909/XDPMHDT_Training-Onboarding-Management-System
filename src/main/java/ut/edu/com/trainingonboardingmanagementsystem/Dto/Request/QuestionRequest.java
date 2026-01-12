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

    @NotBlank
    @Size(max = 255)
    private String content;

    @NotNull
    private QuestionType type;

    @NotEmpty
    @Valid
    private List<ChoiceRequest> choices;
}
