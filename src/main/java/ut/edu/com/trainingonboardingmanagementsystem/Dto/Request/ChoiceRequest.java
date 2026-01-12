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

    @NotBlank
    @Size(max = 255)
    private String content;

    @NotBlank
    @Size(max = 255)
    private String choices;

    @NotNull
    private Boolean isAnswer;

    private Integer score;
    private Integer orderIndex;
}
