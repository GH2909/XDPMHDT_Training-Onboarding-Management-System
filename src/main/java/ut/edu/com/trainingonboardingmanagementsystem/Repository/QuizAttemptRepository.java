package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizAttempt;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {

    List<QuizAttempt> findByEmployeeIdAndQuizIdOrderByAttemptNumberDesc(Integer employeeId, Integer quizId);

    @Query("SELECT COUNT(qa) FROM QuizAttempt qa WHERE qa.employee.id = :employeeId AND qa.quiz.id = :quizId")
    Integer countAttemptsByEmployeeAndQuiz(Integer employeeId, Integer quizId);

    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.employee.id = :employeeId AND qa.quiz.id = :quizId ORDER BY qa.score DESC")
    List<QuizAttempt> findBestAttempt(Integer employeeId, Integer quizId);
}
