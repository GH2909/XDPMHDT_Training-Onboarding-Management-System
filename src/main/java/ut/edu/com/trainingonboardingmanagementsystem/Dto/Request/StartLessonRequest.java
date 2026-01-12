package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartLessonRequest {

    @NotNull
    private Integer lessonId;
}
