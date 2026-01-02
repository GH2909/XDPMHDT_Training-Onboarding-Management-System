//package ut.edu.com.trainingonboardingmanagementsystem.Repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestionId;
//
//import java.util.List;
//
//public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, QuizQuestionId> {
//    List<QuizQuestion> findByQuiz_Id(Integer quizId);
//
//    boolean existsByQuiz_IdAndQuestion_Id(Integer quizId, Integer questionId);
//}
