package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;

import java.util.List;
import java.util.Optional;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    @Query("SELECT a FROM Assignment a JOIN FETCH a.course WHERE a.employee.id = :employeeId")
    List<Assignment> findByEmployeeIdWithCourse(@Param("employeeId") Integer eId);

    @Query("SELECT a FROM Assignment a WHERE a.employee.id = :employeeId AND a.course.id = :courseId")
    Optional<Assignment> findByEmployeeIdAndCourseId(Integer eId, Integer cId);

    boolean existsByEmployeeIdAndCourseId(Integer eId, Integer cId);
}
