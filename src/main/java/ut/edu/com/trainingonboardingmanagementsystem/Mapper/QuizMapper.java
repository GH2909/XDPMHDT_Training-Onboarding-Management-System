package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

@Component
public class QuizMapper {
    public Quiz CreateQuiz (QuizCreateRequest request, Lesson lesson) {
        Quiz qui = new Quiz();
        qui.setLesson(lesson);
        qui.setTitle(request.getTitle());
        qui.setDescription(request.getDescription());
        qui.setCategoryId(request.getCategoryId());
        qui.setDurationMinutes(request.getDurationMinutes());
        qui.setMaxScore(request.getMaxScore());
        qui.setPassScore(request.getPassScore());
        qui.setAttemptLimit(request.getAttemptLimit());
        qui.setStatus(QuizStatus.DRAFT);
        qui.setStartTime(request.getStartTime());
        qui.setEndTime(request.getEndTime());
        qui.setShowAnswers(request.getShowAnswers());
        qui.setAllowReview(request.getAllowReview());
        qui.setShuffleQuestions(request.getShuffleQuestions());
        qui.setShuffleChoices(request.getShuffleChoices());
        qui.setUpdatedAt(request.getUpdatedAt());
        return qui;
    }

    public QuizResponse quizResponse (Quiz quiz) {
        QuizResponse quizRes = new QuizResponse();
        quizRes.setId(quiz.getId());
        quizRes.setTitle(quiz.getTitle());
        quizRes.setDescription(quiz.getDescription());
        quizRes.setMaxScore(quiz.getMaxScore());
        quizRes.setStatus(quiz.getStatus());
        quizRes.setDurationMinutes(quiz.getDurationMinutes());
        return quizRes;
    }
}
