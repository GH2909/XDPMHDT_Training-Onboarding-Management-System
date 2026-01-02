package ut.edu.com.trainingonboardingmanagementsystem.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignRequest {
    private Integer courseId;
    private List<Integer> employeeIds;
}
