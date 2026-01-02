package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LessonRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepo;
    private final LessonRepository lessonRepo;

    public Quiz createQuiz(QuizCreateRequest dto) {

        Lesson lesson = lessonRepo.findById(dto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson không tồn tại"));

        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setLesson(lesson);
        quiz.setDurationMinutes(dto.getDurationMinutes());
        quiz.setAttemptLimit(dto.getAttemptLimit());
        quiz.setStatus(QuizStatus.DRAFT);

        return quizRepo.save(quiz);
    }
}
