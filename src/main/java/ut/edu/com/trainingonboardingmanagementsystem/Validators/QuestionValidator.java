package ut.edu.com.trainingonboardingmanagementsystem.Validators;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChoiceRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ValidationException;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuestionValidator {
    public void validateQuestionRequest(QuestionRequest request) {
        if (request.getChoices() == null || request.getChoices().isEmpty()) {
            throw new ValidationException("Câu hỏi phải có ít nhất một lựa chọn.");
        }

        long correctAnswerCount = request.getChoices().stream()
                .filter(ChoiceRequest::getIsAnswer)
                .count();

        if (correctAnswerCount == 0) {
            throw new ValidationException("Câu hỏi phải có ít nhất một đáp án đúng.");
        }

        if (request.getType() == QuestionType.TRUE_FALSE) {
            if (request.getChoices().size() != 2) {
                throw new ValidationException("Câu hỏi True/False phải có 2 lựa chọn.");
            }

            if (correctAnswerCount != 1) {
                throw new ValidationException("Câu hỏi True/False phải có 1 đáp án đúng.");
            }
        }

        Set<String> choiceLabels = request.getChoices().stream()
                .map(ChoiceRequest::getChoices)
                .collect(Collectors.toSet());

        if (choiceLabels.size() != request.getChoices().size()) {
            throw new ValidationException("Mỗi lựa chọn không được trùng lặp.");
        }
    }
}
