package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuestionCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ApiResponse<QuestionResponse> createQuestion(@Valid @RequestBody QuestionCreateRequest request) {
        ApiResponse<QuestionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(questionService.createQuestion(request));
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionResponse> getQuestion(@PathVariable Integer id, @RequestParam(defaultValue = "false") boolean showAnswers) {
        ApiResponse<QuestionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(questionService.getQuestionById(id, showAnswers));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<QuestionResponse>> getAllQuestions(@RequestParam(defaultValue = "false") boolean showAnswers) {
        ApiResponse<List<QuestionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(questionService.getAllQuestions(showAnswers));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuestion(@PathVariable Integer id) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        questionService.deleteQuestion(id);
        apiResponse.setResult(null);
        return apiResponse;
    }
}
