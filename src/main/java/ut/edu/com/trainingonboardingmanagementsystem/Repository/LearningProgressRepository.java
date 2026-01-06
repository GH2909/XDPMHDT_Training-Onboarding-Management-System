package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.LearningProgress;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

public interface LearningProgressRepository extends JpaRepository<LearningProgress, Integer> {
    long countByStatus(LearningStatus status);
}
