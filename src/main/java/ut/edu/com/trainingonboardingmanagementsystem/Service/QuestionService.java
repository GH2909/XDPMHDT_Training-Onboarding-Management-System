package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChoiceRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuestionMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.ChoiceRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.QuestionValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionValidator questionValidator;

    public QuestionResponse createQuestion(QuestionRequest request) {

        questionValidator.validateQuestionRequest(request);
        Question question = questionMapper.questionCreate(request);
        Question savedQuestion = questionRepository.save(question);

        // Create choices
        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < request.getChoices().size(); i++) {
            ChoiceRequest choiceReq = request.getChoices().get(i);
            Choice choice = new Choice();
            choice.setQuestion(savedQuestion);
            choice.setContent(choiceReq.getContent());
            choice.setChoices(choiceReq.getChoices());
            choice.setIsAnswer(choiceReq.getIsAnswer());
            choice.setScore(choiceReq.getScore());
            choice.setOrderIndex(choiceReq.getOrderIndex());
            choices.add(choice);
        }

        choiceRepository.saveAll(choices);
        savedQuestion.setChoices(choices);

        return questionMapper.questionResponse(savedQuestion, false);
    }

    public QuestionResponse getQuestionById(Integer id, boolean showAnswers) {
        Question question = questionRepository.findByIdWithChoices(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        return questionMapper.questionResponse(question, showAnswers);
    }

    public List<QuestionResponse> getAllQuestions(boolean showAnswers) {
        return questionRepository.findAll().stream()
                .map(question -> questionMapper.questionResponse(question, showAnswers))
                .collect(Collectors.toList());
    }

    public void deleteQuestion(Integer id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }
}

