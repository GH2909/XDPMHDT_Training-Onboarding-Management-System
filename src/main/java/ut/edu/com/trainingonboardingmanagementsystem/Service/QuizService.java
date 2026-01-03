package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuizMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final QuizMapper quizMapper;

    public QuizResponse createQuiz(QuizCreateRequest request) {

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + request.getLessonId()));

        Quiz quiz = quizMapper.CreateQuiz(request, lesson);
        return quizMapper.quizResponse(quiz);
    }

    public QuizResponse updateQuiz(Integer id, QuizCreateRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

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
        return quizMapper.quizResponse(updatedQuiz);
    }

    public QuizResponse getQuizById(Integer id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        return quizMapper.quizResponse(quiz);
    }

    public List<QuizResponse> getQuizzesByLessonId(Integer lessonId) {
        return quizRepository.findByLessonId(lessonId).stream()
                .map(quiz -> quizMapper.quizResponse(quiz))
                .collect(Collectors.toList());
    }

    public void deleteQuiz(Integer id) {
        if (!quizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}
