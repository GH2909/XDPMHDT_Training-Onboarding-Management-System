package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;

@Component
public class QuestionMapper {
    public Question ques (QuestionCreateRequest request) {
        Question ques = new Question();
        ques.setContent(request.getContent());
        ques.setType(request.getType());
        return ques;
    }

    public QuestionResponse questionResponse (Question question, Integer sequencenum) {
        QuestionResponse questionRes = new QuestionResponse();
        questionRes.setId(question.getId());
        questionRes.setContent(question.getContent());
        questionRes.setType(question.getType());
        questionRes.setSequenceNumber(sequencenum);
        return questionRes;
    }
}
