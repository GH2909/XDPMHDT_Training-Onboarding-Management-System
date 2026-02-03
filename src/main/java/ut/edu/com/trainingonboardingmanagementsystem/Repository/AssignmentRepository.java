package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;

import java.util.List;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    @Query("SELECT a FROM Assignment a JOIN FETCH a.course WHERE a.employee.id = :employeeId")
    List<Assignment> findByEmployeeIdWithCourse(@Param("employeeId") Integer eId);

    boolean existsByEmployeeIdAndCourseId(Integer eId, Integer cId);
}
