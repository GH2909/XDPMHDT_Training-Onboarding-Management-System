//package ut.edu.com.trainingonboardingmanagementsystem.Service;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignQuestionRequest;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
//import ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException;
//import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
//import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuestionMapper;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
//import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestionId;
//import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuestionRepository;
//import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizQuestionRepository;
//import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class QuizQuestionService {
//
//    private final QuizRepository quizRepository;
//    private final QuestionRepository questionRepository;
//    private final QuizQuestionRepository quizQuestionRepository;
//    private final QuestionMapper questionMapper;
//
//    public void assignQuestionToQuiz(Integer quizId, AssignQuestionRequest request) {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
//
//        Question question = questionRepository.findById(request.getQuestionId())
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + request.getQuestionId()));
//
//        QuizQuestionId id = new QuizQuestionId(quizId, request.getQuestionId());
//        if (quizQuestionRepository.existsById(id)) {
//            throw new DuplicateResourceException("Question already assigned to this quiz");
//        }
//
//        Integer sequenceNumber = request.getSequenceNumber();
//        if (sequenceNumber == null) {
//            Integer maxSeq = quizQuestionRepository.findMaxSequenceNumberByQuizId(quizId);
//            sequenceNumber = (maxSeq != null ? maxSeq : 0) + 1;
//        }
//
//        QuizQuestion quizQuestion = new QuizQuestion();
//        quizQuestion.setQuizId(quizId);
//        quizQuestion.setQuestionId(request.getQuestionId());
//        quizQuestion.setSequenceNumber(sequenceNumber);
//
//        quizQuestionRepository.save(quizQuestion);
//    }
//
//    public void assignMultipleQuestions(Integer quizId, List<AssignQuestionRequest> requests) {
//        for (AssignQuestionRequest request : requests) {
//            assignQuestionToQuiz(quizId, request);
//        }
//    }
//
//    public List<QuestionResponse> getQuizQuestions(Integer quizId, boolean showAnswers) {
//        List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuizIdWithQuestionsAndChoices(quizId);
//
//        return quizQuestions.stream()
//                .map(qq -> questionMapper.questionResponse(qq.getQuestion(), showAnswers))
//                .collect(Collectors.toList());
//    }
//
//    public void removeQuestionFromQuiz(Integer quizId, Integer questionId) {
//        QuizQuestionId id = new QuizQuestionId(quizId, questionId);
//        if (!quizQuestionRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Question not assigned to this quiz");
//        }
//        quizQuestionRepository.deleteById(id);
//    }
//}
//
