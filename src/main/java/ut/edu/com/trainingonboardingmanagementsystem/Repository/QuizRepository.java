package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;

import java.util.List;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByLessonId(Integer lessonId);
}
