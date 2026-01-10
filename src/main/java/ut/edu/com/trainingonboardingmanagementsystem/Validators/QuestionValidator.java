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
            throw new ValidationException("Question must have at least one choice");
        }

        long correctAnswerCount = request.getChoices().stream()
                .filter(ChoiceRequest::getIsAnswer)
                .count();

        if (correctAnswerCount == 0) {
            throw new ValidationException("Question must have at least one correct answer");
        }

        if (request.getType() == QuestionType.TRUE_FALSE) {
            if (request.getChoices().size() != 2) {
                throw new ValidationException("True/False question must have exactly 2 choices");
            }

            if (correctAnswerCount != 1) {
                throw new ValidationException("True/False question must have exactly 1 correct answer");
            }
        }

        // Validate unique choice labels
        Set<String> choiceLabels = request.getChoices().stream()
                .map(ChoiceRequest::getChoices)
                .collect(Collectors.toSet());

        if (choiceLabels.size() != request.getChoices().size()) {
            throw new ValidationException("Choice labels must be unique");
        }
    }
}
