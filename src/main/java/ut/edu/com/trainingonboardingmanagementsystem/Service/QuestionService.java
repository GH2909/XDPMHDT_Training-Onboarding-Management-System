package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizQuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final QuizQuestionRepository qqRepo;

    @Transactional
    public void CreateQuestion(QuestionCreateRequest request) {

        Quiz quiz = quizRepo.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz không tồn tại"));

        Question ques = new Question();
        ques.setContent(request.getContent());
        ques.setType(request.getType());
        questionRepo.save(ques);

        QuizQuestion quizquest = new QuizQuestion();
        quizquest.setQuiz(quiz);
        quizquest.setQuestion(ques);
        quizquest.setSequenceNumber(request.getSequenceNumber());

        qqRepo.save(quizquest);
    }
}

