package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
    List<Choice> findByQuestionIdOrderByOrderIndex(Integer questionId);
    List<Choice> findByQuestionIdAndIsAnswer(Integer questionId, Boolean isAnswer);
}
