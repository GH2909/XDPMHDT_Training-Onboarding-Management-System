package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.GapAnalysis;

import java.util.List;
import java.util.Optional;

@Repository
public interface GapAnalysisRepository extends JpaRepository<GapAnalysis, Integer> {
    Optional<GapAnalysis> findById(Integer id);

    @Query("SELECT ga FROM GapAnalysis ga WHERE ga.employee.id = :employeeId")
    List<GapAnalysis> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT ga FROM GapAnalysis ga WHERE ga.competency.framework.id = :frameworkId")
    List<GapAnalysis> findByFrameworkId(@Param("frameworkId") Integer frameworkId);
}
