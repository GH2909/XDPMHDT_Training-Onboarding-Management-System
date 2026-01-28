package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignQuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.SubmitQuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.QuizGradingService;
import ut.edu.com.trainingonboardingmanagementsystem.Service.QuizQuestionService;
import ut.edu.com.trainingonboardingmanagementsystem.Service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizQuestionService quizQuestionService;
    private final QuizGradingService quizGradingService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuizResponse>> createQuiz(
            @Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.createQuiz(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Quiz created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponse>> updateQuiz(
            @PathVariable Integer id,
            @Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.updateQuiz(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Quiz updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponse>> getQuiz(@PathVariable Integer id) {
        QuizResponse response = quizService.getQuizById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ApiResponse<List<QuizResponse>>> getQuizzesByLesson(
            @PathVariable Integer lessonId) {
        List<QuizResponse> responses = quizService.getQuizzesByLessonId(lessonId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Quiz deleted successfully"));
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<ApiResponse<Void>> assignQuestion(
            @PathVariable Integer quizId,
            @Valid @RequestBody AssignQuestionRequest request) {
        quizQuestionService.assignQuestionToQuiz(quizId, request);
        return ResponseEntity.ok(ApiResponse.success(null, "Question assigned successfully"));
    }

    @PostMapping("/{quizId}/questions/batch")
    public ResponseEntity<ApiResponse<Void>> assignMultipleQuestions(
            @PathVariable Integer quizId,
            @Valid @RequestBody List<AssignQuestionRequest> requests) {
        quizQuestionService.assignMultipleQuestions(quizId, requests);
        return ResponseEntity.ok(ApiResponse.success(null, "Questions assigned successfully"));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuizQuestions(
            @PathVariable Integer quizId,
            @RequestParam(defaultValue = "false") boolean showAnswers) {
        List<QuestionResponse> responses = quizQuestionService.getQuizQuestions(quizId, showAnswers);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> removeQuestion(
            @PathVariable Integer quizId,
            @PathVariable Integer questionId) {
        quizQuestionService.removeQuestionFromQuiz(quizId, questionId);
        return ResponseEntity.ok(ApiResponse.success(null, "Question removed successfully"));
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<QuizResultResponse>> submitQuiz(
            @Valid @RequestBody SubmitQuizRequest request) {
        QuizResultResponse result = quizGradingService.gradeQuiz(request);
        return ResponseEntity.ok(ApiResponse.success(result, "Quiz graded successfully"));
    }
}
