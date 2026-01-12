package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonForEmployeeResponse {
    private Integer id;
    private String title;
    private Integer duration;
    private String description;
    private Boolean completed;
    private List<QuizForEmployeeResponse> quizzes;
}
