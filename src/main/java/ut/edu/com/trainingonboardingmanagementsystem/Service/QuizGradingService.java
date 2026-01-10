package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AnswerRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.SubmitQuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Question;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Model.QuizQuestion;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.ChoiceRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizQuestionRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizRepository;
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
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizValidator quizValidator;

    public QuizResultResponse gradeQuiz(SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));

        // Validate quiz availability
        quizValidator.validateQuizAvailability(quiz);

        List<QuizQuestion> quizQuestions = quizQuestionRepository
                .findByQuizIdWithQuestionsAndChoices(request.getQuizId());

        int totalScore = 0;
        int correctAnswers = 0;
        List<QuestionResultResponse> questionResults = new ArrayList<>();

        for (AnswerRequest answer : request.getAnswers()) {
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

            // Calculate score for this question
            int earnedScore = 0;
            boolean isCorrect = false;

            if (question.getType() == QuestionType.TRUE_FALSE) {
                // For True/False, must select exactly the correct choice
                if (answer.getChoiceIds().size() == 1 &&
                        correctChoiceIds.contains(answer.getChoiceIds().get(0))) {
                    earnedScore = correctChoices.get(0).getScore();
                    isCorrect = true;
                }
            } else {
                // For Multiple Choice
                Set<Integer> selectedSet = new HashSet<>(answer.getChoiceIds());
                Set<Integer> correctSet = new HashSet<>(correctChoiceIds);

                if (selectedSet.equals(correctSet)) {
                    earnedScore = correctChoices.stream()
                            .mapToInt(Choice::getScore)
                            .sum();
                    isCorrect = true;
                }
            }

            totalScore += earnedScore;
            if (isCorrect) {
                correctAnswers++;
            }

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
                .userId(request.getUserId())
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