package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizAnswerRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.SubmitQuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.*;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.QuestionMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.*;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.*;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.EmployeeValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeQuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
//    private final ChoiceRepository choiceRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final LearningProgressRepository learningProgressRepository;
    private final EmployeeValidator employeeValidator;
    private final QuestionMapper questionMapper;

    public List<QuestionResponse> getQuizQuestions(Integer employeeId, Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        // Validate employee has access to this quiz's course
        Integer courseId = quiz.getLesson().getCourse().getId();
        employeeValidator.validateCourseAccess(employeeId, courseId);

        List<QuizQuestion> quizQuestions = quizQuestionRepository
                .findByQuizIdWithQuestionsAndChoices(quizId);

        // Don't show answers to students
        return quizQuestions.stream()
                .map(qq -> questionMapper.questionResponse(qq.getQuestion(), false))
                .collect(Collectors.toList());
    }

    public QuizResultResponse submitQuiz(Integer employeeId, SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        // Validate quiz attempt
        employeeValidator.validateQuizAttempt(employeeId, request.getQuizId(), quiz);

        // Validate employee has access
        Integer courseId = quiz.getLesson().getCourse().getId();
        employeeValidator.validateCourseAccess(employeeId, courseId);

        // Get quiz questions
        List<QuizQuestion> quizQuestions = quizQuestionRepository
                .findByQuizIdWithQuestionsAndChoices(request.getQuizId());

        // Grade the quiz
        QuizResultResponse result = gradeQuiz(employeeId, quiz, quizQuestions, request);

        // Save attempt
        saveQuizAttempt(employeeId, quiz, result);

        // Update learning progress
        updateLearningProgress(employeeId, courseId, result);

        return result;
    }

    private QuizResultResponse gradeQuiz(
            Integer employeeId,
            Quiz quiz,
            List<QuizQuestion> quizQuestions,
            SubmitQuizRequest request) {

        int totalScore = 0;
        int correctAnswers = 0;
        List<QuestionResultResponse> questionResults = new ArrayList<>();

        for (QuizAnswerRequest answer : request.getAnswers()) {
            QuizQuestion quizQuestion = quizQuestions.stream()
                    .filter(qq -> qq.getQuestionId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found in quiz"));

            Question question = quizQuestion.getQuestion();
            List<Choice> correctChoices = question.getChoices().stream()
                    .filter(Choice::getIsAnswer)
                    .collect(Collectors.toList());

            List<Integer> correctChoiceIds = correctChoices.stream()
                    .map(Choice::getId)
                    .collect(Collectors.toList());

            // Grade this question
            boolean isCorrect = false;
            int earnedScore = 0;

            Set<Integer> selectedSet = new HashSet<>(answer.getChoiceIds());
            Set<Integer> correctSet = new HashSet<>(correctChoiceIds);

            if (selectedSet.equals(correctSet)) {
                earnedScore = correctChoices.stream()
                        .mapToInt(Choice::getScore)
                        .sum();
                isCorrect = true;
                correctAnswers++;
            }

            totalScore += earnedScore;

            int maxQuestionScore = correctChoices.stream()
                    .mapToInt(Choice::getScore)
                    .sum();

            // Build choice results
            List<ChoiceResultResponse> choiceResults = question.getChoices().stream()
                    .map(choice -> ChoiceResultResponse.builder()
                            .id(choice.getId())
                            .content(choice.getContent())
                            .choiceLabel(choice.getChoices())
                            .isCorrect(choice.getIsAnswer())
                            .wasSelected(selectedSet.contains(choice.getId()))
                            .build())
                    .collect(Collectors.toList());

            questionResults.add(QuestionResultResponse.builder()
                    .questionId(question.getId())
                    .questionContent(question.getContent())
                    .questionType(question.getType())
                    .selectedChoiceIds(answer.getChoiceIds())
                    .correctChoiceIds(correctChoiceIds)
                    .earnedScore(earnedScore)
                    .maxScore(maxQuestionScore)
                    .correct(isCorrect)
                    .choices(quiz.getAllowReview() ? choiceResults : null)
                    .build());
        }

        boolean passed = totalScore >= quiz.getPassScore();
        double percentage = (double) totalScore / quiz.getMaxScore() * 100;

        Integer attemptCount = quizAttemptRepository.countAttemptsByEmployeeAndQuiz(employeeId, quiz.getId());
        Integer remainingAttempts = quiz.getAttemptLimit() - attemptCount - 1;

        return QuizResultResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getTitle())
                .attemptNumber(attemptCount + 1)
                .totalScore(totalScore)
                .maxScore(quiz.getMaxScore())
                .passScore(quiz.getPassScore())
                .passed(passed)
                .percentage(percentage)
                .correctAnswers(correctAnswers)
                .totalQuestions(quizQuestions.size())
                .submittedAt(LocalDateTime.now())
                .remainingAttempts(remainingAttempts)
                .questionResults(questionResults)
                .build();
    }

    private void saveQuizAttempt(Integer employeeId, Quiz quiz, QuizResultResponse result) {
        User employee = new User();
        employee.setId(employeeId);

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .employee(employee)
                .attemptNumber(result.getAttemptNumber())
                .score(result.getTotalScore())
                .maxScore(result.getMaxScore())
                .passed(result.getPassed())
                .submittedAt(result.getSubmittedAt())
                .timeTakenMinutes(result.getTimeTakenMinutes())
                .build();

        quizAttemptRepository.save(attempt);
    }

    private void updateLearningProgress(Integer employeeId, Integer courseId, QuizResultResponse result) {
        LearningProgress progress = learningProgressRepository
                .findByEmployeeIdAndCourseId(employeeId, courseId)
                .orElse(null);

        if (progress != null && result.getPassed()) {
            float newScore = (progress.getScore() + result.getTotalScore()) / 2;
            progress.setScore(newScore);

            learningProgressRepository.save(progress);
        }
    }

    public List<QuizAttemptSummaryResponse> getQuizAttemptHistory(Integer employeeId, Integer quizId) {
        List<QuizAttempt> attempts = quizAttemptRepository
                .findByEmployeeIdAndQuizIdOrderByAttemptNumberDesc(employeeId, quizId);

        return attempts.stream()
                .map(attempt -> QuizAttemptSummaryResponse.builder()
                        .attemptNumber(attempt.getAttemptNumber())
                        .score(attempt.getScore())
                        .maxScore(attempt.getMaxScore())
                        .passed(attempt.getPassed())
                        .percentage((float) attempt.getScore() / attempt.getMaxScore() * 100)
                        .submittedAt(attempt.getSubmittedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
