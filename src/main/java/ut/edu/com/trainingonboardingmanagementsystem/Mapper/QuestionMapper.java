package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ChoiceResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public Question questionCreate (QuestionRequest request) {
        Question question = new Question();
        question.setContent(request.getContent());
        question.setType(request.getType());
        return question;
    }

    public QuestionResponse questionResponse (Question question, Boolean showAnswers) {

        List<ChoiceResponse> choiceResponses = question.getChoices().stream()
                .map(choice -> toChoiceResponse(choice, showAnswers))
                .collect(Collectors.toList());

        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .type(question.getType())
                .choices(choiceResponses)
                .build();

    }
    public ChoiceResponse toChoiceResponse(Choice choice, boolean showAnswers) {
        return ChoiceResponse.builder()
                .id(choice.getId())
                .content(choice.getContent())
                .choices(choice.getChoices())
                .isAnswer(showAnswers ? choice.getIsAnswer() : null)
                .score(choice.getScore())
                .orderIndex(choice.getOrderIndex())
                .build();
    }
}
