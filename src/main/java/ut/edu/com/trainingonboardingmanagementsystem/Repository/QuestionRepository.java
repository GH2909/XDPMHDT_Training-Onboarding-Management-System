package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

import java.util.List;
import java.util.Optional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByType(QuestionType type);

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.choices WHERE q.id = :id")
    Optional<Question> findByIdWithChoices(Integer id);
}
