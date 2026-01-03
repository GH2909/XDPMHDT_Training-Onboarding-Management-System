package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ApiResponse<LessonResponse> createLesson(@Valid @RequestBody LessonRequest request, Course course, User user) {
        ApiResponse<LessonResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonService.createLesson(request, course, user));
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<LessonResponse> updateLesson(@PathVariable Integer id, @Valid @RequestBody LessonRequest request) {
        ApiResponse<LessonResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonService.updateLesson(id, request));
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonResponse> getLesson(@PathVariable Integer id) {
        ApiResponse<LessonResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonService.getLessonById(id));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<LessonResponse>> getAllLessons() {
        ApiResponse<List<LessonResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(lessonService.getAllLessons());
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Integer id) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        lessonService.deleteLesson(id);
        apiResponse.setResult(null);
        return apiResponse;
    }
}
