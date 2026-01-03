package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizQuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;

@Component
public class QuizQuestionMapper {

    public QuizQuestionResponse quizQuestionResponse (QuizQuestion quizQuestion) {
        QuizQuestionResponse quizQuestionRes = new QuizQuestionResponse();
        quizQuestionRes.setQuizId(quizQuestion.getQuiz().getId());
        quizQuestionRes.setQuestionId(quizQuestion.getQuestion().getId());
        quizQuestionRes.setQuestionContent(quizQuestion.getQuestion().getContent());
        quizQuestionRes.setSequenceNumber(quizQuestion.getSequenceNumber());
        return quizQuestionRes;
    }
}
