package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

@Component
public class QuizMapper {
    public Quiz CreateQuiz (QuizRequest request, Lesson lesson) {
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

    public QuizResponse quizResponse(Quiz quiz, Integer questionCount) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .lessonId(quiz.getLesson().getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .categoryId(quiz.getCategoryId())
                .durationMinutes(quiz.getDurationMinutes())
                .maxScore(quiz.getMaxScore())
                .passScore(quiz.getPassScore())
                .attemptLimit(quiz.getAttemptLimit())
                .status(quiz.getStatus())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .showAnswers(quiz.getShowAnswers())
                .allowReview(quiz.getAllowReview())
                .shuffleQuestions(quiz.getShuffleQuestions())
                .shuffleChoices(quiz.getShuffleChoices())
                .updatedAt(quiz.getUpdatedAt())
                .questionCount(questionCount)
                .build();
    }
}
