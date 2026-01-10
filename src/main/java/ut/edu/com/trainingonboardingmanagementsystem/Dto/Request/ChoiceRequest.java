package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {

    @NotBlank(message = "Content is required")
    @Size(max = 255, message = "Content must not exceed 255 characters")
    private String content;

    @NotBlank(message = "Choices label is required")
    @Size(max = 255, message = "Choices must not exceed 255 characters")
    private String choices;

    @NotNull(message = "isAnswer flag is required")
    private Boolean isAnswer;

    private Integer score;
    private Integer orderIndex;
}
