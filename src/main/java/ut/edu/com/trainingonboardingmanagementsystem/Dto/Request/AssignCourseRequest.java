package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AssignCourseRequest {
    private Integer courseId;
    private List<Integer> employeeIds;
}
