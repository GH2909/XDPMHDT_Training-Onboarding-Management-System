package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.SubmitQuizRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizAttemptSummaryResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResultResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.EmployeeQuizService;

import java.util.List;

@RestController
@RequestMapping("/employee/quizzes")
@RequiredArgsConstructor
@Validated
public class EmployeeQuizController {

    private final EmployeeQuizService employeeQuizService;

    @GetMapping("/{employeeId}/{quizId}/questions")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuizQuestions(
            @PathVariable Integer employeeId,
            @PathVariable Integer quizId) {

        List<QuestionResponse> questions = employeeQuizService
                .getQuizQuestions(employeeId, quizId);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @PostMapping("/{employeeId}/submit")
    public ResponseEntity<ApiResponse<QuizResultResponse>> submitQuiz(
            @PathVariable Integer employeeId,
            @Valid @RequestBody SubmitQuizRequest request) {

        QuizResultResponse result = employeeQuizService
                .submitQuiz(employeeId, request);
        return ResponseEntity.ok(ApiResponse.success(result, "Quiz submitted successfully"));
    }

    @GetMapping("/{employeeId}/{quizId}/attempts")
    public ResponseEntity<ApiResponse<List<QuizAttemptSummaryResponse>>> getQuizAttempts(
            @PathVariable Integer employeeId,
            @PathVariable Integer quizId) {

        List<QuizAttemptSummaryResponse> attempts = employeeQuizService
                .getQuizAttemptHistory(employeeId, quizId);
        return ResponseEntity.ok(ApiResponse.success(attempts));
    }
}
