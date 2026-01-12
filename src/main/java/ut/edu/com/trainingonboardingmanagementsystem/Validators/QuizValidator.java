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
            throw new ValidationException("Lỗi");
        }

        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getEndTime().isBefore(request.getStartTime())) {
                throw new ValidationException("Lỗi");
            }
        }

        if (request.getDurationMinutes() <= 0) {
            throw new ValidationException("Thời lượng phải là số dương.");
        }
    }

    public void validateQuizAvailability(Quiz quiz) {
        if (quiz.getStatus() == QuizStatus.CLOSED) {
            throw new QuizNotAvailableException("Bài kiểm tra đã đóng.");
        }

        if (quiz.getStatus() == QuizStatus.DRAFT) {
            throw new QuizNotAvailableException("Bài kiểm tra chưa được công bố.");
        }

        LocalDateTime now = LocalDateTime.now();

        if (quiz.getStartTime() != null && now.isBefore(quiz.getStartTime())) {
            throw new QuizNotAvailableException("Bài kiểm tra chưa bắt đầu.");
        }

        if (quiz.getEndTime() != null && now.isAfter(quiz.getEndTime())) {
            throw new QuizNotAvailableException("Bài kiểm tra đã kết thúc.");
        }
    }
}