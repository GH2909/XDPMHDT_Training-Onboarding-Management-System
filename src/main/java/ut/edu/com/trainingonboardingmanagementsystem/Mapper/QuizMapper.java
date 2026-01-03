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
        Quiz quiz = new Quiz();
        quiz.setLesson(lesson);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setCategoryId(request.getCategoryId());
        quiz.setDurationMinutes(request.getDurationMinutes());
        quiz.setMaxScore(request.getMaxScore());
        quiz.setPassScore(request.getPassScore());
        quiz.setAttemptLimit(request.getAttemptLimit());
        quiz.setStatus(QuizStatus.DRAFT);
        quiz.setStartTime(request.getStartTime());
        quiz.setEndTime(request.getEndTime());
        quiz.setShowAnswers(request.getShowAnswers());
        quiz.setAllowReview(request.getAllowReview());
        quiz.setShuffleQuestions(request.getShuffleQuestions());
        quiz.setShuffleChoices(request.getShuffleChoices());
        return quiz;
    }

    public QuizResponse quizResponse (Quiz quiz) {
        QuizResponse quizRes = new QuizResponse();
        quizRes.setId(quiz.getId());
        quizRes.setLessonId(quiz.getLesson().getId());
        quizRes.setTitle(quiz.getTitle());
        quizRes.setDescription(quiz.getDescription());
        quizRes.setCategoryId(quiz.getCategoryId());
        quizRes.setMaxScore(quiz.getMaxScore());
        quizRes.setPassScore(quiz.getPassScore());
        quizRes.setStatus(quiz.getStatus());
        quizRes.setAttemptLimit(quiz.getAttemptLimit());
        quizRes.setDurationMinutes(quiz.getDurationMinutes());
        quizRes.setStartTime(quiz.getStartTime());
        quizRes.setEndTime(quiz.getEndTime());
        quizRes.setShowAnswers(quiz.getShowAnswers());
        quizRes.setAllowReview(quiz.getAllowReview());
        quizRes.setShuffleQuestions(quiz.getShuffleQuestions());
        quizRes.setShuffleChoices(quiz.getShuffleChoices());
        quizRes.setUpdatedAt(quiz.getUpdatedAt());
        return quizRes;
    }
}
