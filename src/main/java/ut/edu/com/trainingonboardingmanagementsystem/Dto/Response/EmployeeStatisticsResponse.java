package ut.edu.com.trainingonboardingmanagementsystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeStatisticsResponse {
    private Long totalAssignedCourses;
    private Long completedCourses;
    private Long inProgressCourses;
    private Float averageScore;
    private Integer totalQuizzesTaken;
    private Integer totalQuizzesPassed;
}
