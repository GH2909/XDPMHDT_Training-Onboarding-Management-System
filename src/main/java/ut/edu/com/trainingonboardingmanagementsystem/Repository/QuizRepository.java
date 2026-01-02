package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
