package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Integer id;
    private String title;
    private Integer duration;
    private String description;
    private Integer courseId;
    private Integer createdBy;
}
