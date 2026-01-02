package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    boolean existsByEmployeeIdAndCourseId(Integer eId, Integer cId);
}
