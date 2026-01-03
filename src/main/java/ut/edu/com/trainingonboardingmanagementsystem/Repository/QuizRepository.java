package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.util.List;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByLessonId(Integer lessonId);
    List<Quiz> findByStatus(QuizStatus status);

    @Query("SELECT q FROM Quiz q WHERE q.lesson.id = :lessonId AND q.status = :status")
    List<Quiz> findByLessonIdAndStatus(Integer lessonId, QuizStatus status);
}
