package ut.edu.com.trainingonboardingmanagementsystem.Service;

import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizQuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizQuestionResponse;

import java.util.List;

public interface QuizQuestionService {
    QuizQuestionResponse addQuestionToQuiz(QuizQuestionRequest request);
    List<QuizQuestionResponse> getQuestionsByQuiz(Integer quizId);
}

