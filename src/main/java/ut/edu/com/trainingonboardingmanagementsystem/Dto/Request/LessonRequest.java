package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {

    @NotNull
    private String courseName;

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotNull
    @Positive
    private Integer duration;

    private String description;
//    private String createdBy;
}
