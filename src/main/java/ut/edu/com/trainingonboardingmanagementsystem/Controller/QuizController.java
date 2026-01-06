//package ut.edu.com.trainingonboardingmanagementsystem.Controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignQuestionRequest;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.QuizCreateRequest;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuestionResponse;
//import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.QuizResponse;
//import ut.edu.com.trainingonboardingmanagementsystem.Service.QuizQuestionService;
//import ut.edu.com.trainingonboardingmanagementsystem.Service.QuizService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/quizzes")
//@RequiredArgsConstructor
//public class QuizController {
//
//    private final QuizService quizService;
//    private final QuizQuestionService quizQuestionService;
//
//    @PostMapping
//    public ApiResponse<QuizResponse> createQuiz(@Valid @RequestBody QuizCreateRequest request) {
//        ApiResponse<QuizResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(quizService.createQuiz(request));
//        return apiResponse;
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<QuizResponse> updateQuiz(@PathVariable Integer id, @Valid @RequestBody QuizCreateRequest request) {
//        ApiResponse<QuizResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(quizService.updateQuiz(id, request));
//        return apiResponse;
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<QuizResponse> getQuiz(@PathVariable Integer id) {
//        ApiResponse<QuizResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(quizService.getQuizById(id));
//        return apiResponse;
//    }
//
//    @GetMapping("/lesson/{lessonId}")
//    public ApiResponse<List<QuizResponse>> getQuizzesByLesson(@PathVariable Integer lessonId) {
//        ApiResponse<List<QuizResponse>> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(quizService.getQuizzesByLessonId(lessonId));
//        return apiResponse;
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> deleteQuiz(@PathVariable Integer id) {
//        ApiResponse<Void> apiResponse = new ApiResponse<>();
//        quizService.deleteQuiz(id);
//        apiResponse.setResult(null);
//        return apiResponse;
//    }
//
//    // Question-Quiz
//    @PostMapping("/{quizId}/questions")
//    public ApiResponse<Void> assignQuestion(@PathVariable Integer quizId, @Valid @RequestBody AssignQuestionRequest request) {
//        ApiResponse<Void> apiResponse = new ApiResponse<>();
//        quizQuestionService.assignQuestionToQuiz(quizId,request);
//        apiResponse.setResult(null);
//        return apiResponse;
//    }
//
//    @PostMapping("/{quizId}/questions/batch")
//    public ApiResponse<Void> assignMultipleQuestion(@PathVariable Integer quizId, @Valid @RequestBody List<AssignQuestionRequest> requests) {
//        ApiResponse<Void> apiResponse = new ApiResponse<>();
//        quizQuestionService.assignMultipleQuestions(quizId,requests);
//        apiResponse.setResult(null);
//        return apiResponse;
//    }
//
//    @GetMapping("/{quizId}/questions")
//    public ApiResponse<List<QuestionResponse>> getQuizQuestions(@PathVariable Integer quizId, @RequestParam(defaultValue = "false") boolean showAnswers) {
//        ApiResponse<List<QuestionResponse>> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(quizQuestionService.getQuizQuestions(quizId,showAnswers));
//        return apiResponse;
//    }
//
//    @DeleteMapping("/{quizId}/questions/{questionId}")
//    public ApiResponse<Void> deleteQuestion(
//            @PathVariable Integer quizId,
//            @PathVariable Integer questionId) {
//        ApiResponse<Void> apiResponse = new ApiResponse<>();
//        quizQuestionService.removeQuestionFromQuiz(quizId, questionId);
//        apiResponse.setResult(null);
//        return apiResponse;
//    }
//}
