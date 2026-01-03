package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {
    private Integer courseId;
    private String title;
    private Integer duration;
    private String description;
    private Integer createdBy;
}
