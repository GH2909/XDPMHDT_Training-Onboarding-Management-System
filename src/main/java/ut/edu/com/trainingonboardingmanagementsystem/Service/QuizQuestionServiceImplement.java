package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizQuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizQuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.BusinessException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuizQuestionMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestionId;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizQuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImplement implements QuizQuestionService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final QuizQuestionRepository quizQuestionRepo;
    private final QuizQuestionMapper mapper;

    @Override
    public QuizQuestionResponse addQuestionToQuiz(QuizQuestionRequest request) {

        Quiz quiz = quizRepo.findById(request.getQuizId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Quiz không tồn tại"));

        Question question = questionRepo.findById(request.getQuestionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question không tồn tại"));

        if (quizQuestionRepo.existsByQuiz_IdAndQuestion_Id(
                quiz.getId(), question.getId())) {
            throw new BusinessException("Câu hỏi đã tồn tại trong quiz");
        }

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(new QuizQuestionId(
                quiz.getId(), question.getId()));
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);
        quizQuestion.setSequenceNumber(request.getSequenceNumber());

        return mapper.quizQuestionResponse(
                quizQuestionRepo.save(quizQuestion));
    }

    @Override
    public List<QuizQuestionResponse> getQuestionsByQuiz(Integer quizId) {
        return quizQuestionRepo.findByQuiz_Id(quizId)
                .stream()
                .map(mapper::quizQuestionResponse)
                .toList();
    }
}

