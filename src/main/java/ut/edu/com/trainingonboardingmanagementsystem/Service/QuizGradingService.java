package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizAnswerRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.SubmitQuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.*;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.QuizValidator;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuestionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizGradingService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizValidator quizValidator;

    public QuizResultResponse gradeQuiz(SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));

        // Validate có làm đuọc không
        quizValidator.validateQuizAvailability(quiz);

        // Lấy tất cả questions của quiz (với choices)
        List<QuizQuestion> quizQuestions = quizQuestionRepository
                .findByQuizIdWithQuestionsAndChoices(request.getQuizId());

        // Chấm điểm
        QuizResultResponse result = gradeQuizAnswers(quiz, quizQuestions, request);

        return result;
    }
    private QuizResultResponse gradeQuizAnswers(
            Quiz quiz,
            List<QuizQuestion> quizQuestions,
            SubmitQuizRequest request) {
        int totalScore = 0;
        int correctAnswers = 0;
        List<QuestionResultResponse> questionResults = new ArrayList<>();

        // Duyệt qua từng câu trả lời
        for (QuizAnswerRequest answer : request.getAnswers()) {
            // Tìm question
            QuizQuestion quizQuestion = quizQuestions.stream()
                    .filter(qq -> qq.getQuestionId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found in quiz"));

            Question question = quizQuestion.getQuestion();
            // Lấy tất cả đáp án ĐÚNG
            List<Choice> correctChoices = question.getChoices().stream()
                    .filter(Choice::getIsAnswer)
                    .collect(Collectors.toList());

            List<Integer> correctChoiceIds = correctChoices.stream()
                    .map(Choice::getId)
                    .collect(Collectors.toList());

            // LOGIC CHẤM ĐIỂM
            int earnedScore = 0;
            boolean isCorrect = false;

            if (question.getType() == QuestionType.TRUE_FALSE) {
                // True/False
                if (answer.getChoiceIds().size() == 1 &&
                        correctChoiceIds.contains(answer.getChoiceIds().get(0))) {
                    earnedScore = correctChoices.get(0).getScore();
                    isCorrect = true;
                    correctAnswers++;
                }
            } else {
                // Multiple Choice
                Set<Integer> selectedSet = new HashSet<>(answer.getChoiceIds());
                Set<Integer> correctSet = new HashSet<>(correctChoiceIds);

                // So sánh: Đã chọn = Đáp án đúng?
                if (selectedSet.equals(correctSet)) {
                    // Đúng tính điểm
                    earnedScore = correctChoices.stream()
                            .mapToInt(Choice::getScore)
                            .sum();
                    isCorrect = true;
                    correctAnswers++;
                }
            }

            totalScore += earnedScore;

            int maxQuestionScore = correctChoices.stream()
                    .mapToInt(Choice::getScore)
                    .sum();

            questionResults.add(QuestionResultResponse.builder()
                    .questionId(question.getId())
                    .questionContent(question.getContent())
                    .selectedChoiceIds(answer.getChoiceIds())
                    .correctChoiceIds(correctChoiceIds)
                    .earnedScore(earnedScore)
                    .maxScore(maxQuestionScore)
                    .correct(isCorrect)
                    .build());
        }

        boolean passed = totalScore >= quiz.getPassScore();
        double percentage = (double) totalScore / quiz.getMaxScore() * 100;

        return QuizResultResponse.builder()
                .quizId(quiz.getId())
                .totalScore(totalScore)
                .maxScore(quiz.getMaxScore())
                .passScore(quiz.getPassScore())
                .passed(passed)
                .percentage(percentage)
                .correctAnswers(correctAnswers)
                .totalQuestions(quizQuestions.size())
                .submittedAt(LocalDateTime.now())
                .questionResults(questionResults)
                .build();
    }
}