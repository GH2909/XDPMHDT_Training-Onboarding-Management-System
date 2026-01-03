package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ChoiceResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public Question questionCreate (QuestionCreateRequest request) {
        Question question = new Question();
        question.setContent(request.getContent());
        question.setType(request.getType());
        return question;
    }

    public QuestionResponse questionResponse (Question question, Boolean showAnswers) {

        List<ChoiceResponse> choiceResponses = question.getChoices().stream()
                .map(choice -> toChoiceResponse(choice, showAnswers))
                .collect(Collectors.toList());

        QuestionResponse questionRes = new QuestionResponse();
        questionRes.setId(question.getId());
        questionRes.setContent(question.getContent());
        questionRes.setType(question.getType());
        questionRes.setChoices(choiceResponses);

        return questionRes;

    }
    public ChoiceResponse toChoiceResponse(Choice choice, boolean showAnswers) {
       ChoiceResponse choices = new ChoiceResponse();
       choices.setId(choice.getId());
       choices.setContent(choice.getContent());
       choices. setChoices(choice.getChoices());
       choices.setIsAnswer(showAnswers ? choice.getIsAnswer() : null);
       choices.setScore(choice.getScore());
       choices.setOrderIndex(choice.getOrderIndex());
       return choices;
    }
}
