package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyFramework;

import java.util.Optional;

@Repository
public interface CompetencyFrameworkRepository extends JpaRepository<CompetencyFramework, Integer> {
    Optional<CompetencyFramework> findById(Integer id);
    boolean existsByName(String name);
}
