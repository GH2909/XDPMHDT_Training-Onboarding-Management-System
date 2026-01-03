package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestionId;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, QuizQuestionId> {
    List<QuizQuestion> findByQuizIdOrderBySequenceNumber(Integer quizId);

    @Query("SELECT qq FROM QuizQuestion qq JOIN FETCH qq.question q LEFT JOIN FETCH q.choices WHERE qq.quizId = :quizId ORDER BY qq.sequenceNumber")
    List<QuizQuestion> findByQuizIdWithQuestionsAndChoices(Integer quizId);

    void deleteByQuizId(Integer quizId);

    @Query("SELECT MAX(qq.sequenceNumber) FROM QuizQuestion qq WHERE qq.quizId = :quizId")
    Integer findMaxSequenceNumberByQuizId(Integer quizId);
}
