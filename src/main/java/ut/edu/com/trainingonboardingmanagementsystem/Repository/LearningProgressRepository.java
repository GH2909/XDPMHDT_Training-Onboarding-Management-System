package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.LearningProgress;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Integer> {
    Optional<LearningProgress> findByEmployeeIdAndCourseId(Integer eId, Integer cId);

    @Query("SELECT COUNT(lp) FROM LearningProgress lp WHERE lp.status = 'PASSED'")
    Long countByStatus(LearningStatus status);
}
