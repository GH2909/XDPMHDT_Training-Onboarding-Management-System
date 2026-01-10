package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuizMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizQuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.QuizValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final QuizMapper quizMapper;
    private final QuizValidator quizValidator;
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizResponse createQuiz(QuizRequest request) {

        quizValidator.validateQuizRequest(request);
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + request.getLessonId()));

        Quiz quiz = quizMapper.CreateQuiz(request, lesson);
        Quiz savedQuiz = quizRepository.save(quiz);

        return quizMapper.quizResponse(quiz, 0);
    }

    public QuizResponse updateQuiz(Integer id, QuizRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        quizValidator.validateQuizRequest(request);

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setDurationMinutes(request.getDurationMinutes());
        quiz.setMaxScore(request.getMaxScore());
        quiz.setPassScore(request.getPassScore());
        quiz.setAttemptLimit(request.getAttemptLimit());
        quiz.setStatus(request.getStatus());
        quiz.setStartTime(request.getStartTime());
        quiz.setEndTime(request.getEndTime());
        quiz.setShowAnswers(request.getShowAnswers());
        quiz.setAllowReview(request.getAllowReview());
        quiz.setShuffleQuestions(request.getShuffleQuestions());
        quiz.setShuffleChoices(request.getShuffleChoices());

        Quiz updatedQuiz = quizRepository.save(quiz);
        int questionCount = quizQuestionRepository.findByQuizIdOrderBySequenceNumber(id).size();

        return quizMapper.quizResponse(updatedQuiz, questionCount);
    }

    public QuizResponse getQuizById(Integer id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        int questionCount = quizQuestionRepository.findByQuizIdOrderBySequenceNumber(id).size();
        return quizMapper.quizResponse(quiz, questionCount);
    }

    public List<QuizResponse> getQuizzesByLessonId(Integer lessonId) {
        return quizRepository.findByLessonId(lessonId).stream()
                .map(quiz -> {
                    int questionCount = quizQuestionRepository.findByQuizIdOrderBySequenceNumber(quiz.getId()).size();
                    return quizMapper.quizResponse(quiz, questionCount);
                })
                .collect(Collectors.toList());
    }

    public void deleteQuiz(Integer id) {
        if (!quizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}
