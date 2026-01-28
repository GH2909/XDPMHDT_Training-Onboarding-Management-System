package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(
            @Valid @RequestBody QuestionRequest request) {
        QuestionResponse response = questionService.createQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Question created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionResponse>> getQuestion(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "false") boolean showAnswers) {
        QuestionResponse response = questionService.getQuestionById(id, showAnswers);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getAllQuestions(
            @RequestParam(defaultValue = "false") boolean showAnswers) {
        List<QuestionResponse> responses = questionService.getAllQuestions(showAnswers);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Question deleted successfully"));
    }
}
