package ut.edu.com.trainingonboardingmanagementsystem.Validators;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.QuizNotAvailableException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ValidationException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.time.LocalDateTime;

@Component
public class QuizValidator {

    public void validateQuizRequest(QuizRequest request) {
        if (request.getPassScore() > request.getMaxScore()) {
            throw new ValidationException("Pass score cannot be greater than max score");
        }

        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getEndTime().isBefore(request.getStartTime())) {
                throw new ValidationException("End time must be after start time");
            }
        }

        if (request.getDurationMinutes() <= 0) {
            throw new ValidationException("Duration must be positive");
        }
    }

    public void validateQuizAvailability(Quiz quiz) {
        if (quiz.getStatus() == QuizStatus.CLOSED) {
            throw new QuizNotAvailableException("Quiz is closed");
        }

        if (quiz.getStatus() == QuizStatus.DRAFT) {
            throw new QuizNotAvailableException("Quiz is not published yet");
        }

        LocalDateTime now = LocalDateTime.now();

        if (quiz.getStartTime() != null && now.isBefore(quiz.getStartTime())) {
            throw new QuizNotAvailableException("Quiz has not started yet");
        }

        if (quiz.getEndTime() != null && now.isAfter(quiz.getEndTime())) {
            throw new QuizNotAvailableException("Quiz has ended");
        }
    }
}