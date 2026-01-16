package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyList;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetencyListRepository extends JpaRepository<CompetencyList, Integer> {
    Optional<CompetencyList> findById(Integer id);

    @Query("SELECT cl FROM CompetencyList cl WHERE cl.framework.id = :frameworkId")
    List<CompetencyList> findByFrameworkId(@Param("frameworkId") Integer frameworkId);
}
