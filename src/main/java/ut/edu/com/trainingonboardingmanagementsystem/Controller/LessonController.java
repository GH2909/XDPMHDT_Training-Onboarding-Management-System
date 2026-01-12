package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.LessonRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LessonResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Validated
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @Valid @RequestBody LessonRequest request) {
        LessonResponse response = lessonService.createLesson(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Lesson created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLesson(
            @PathVariable Integer id,
            @Valid @RequestBody LessonRequest request) {
        LessonResponse response = lessonService.updateLesson(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Lesson updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> getLesson(@PathVariable Integer id) {
        LessonResponse response = lessonService.getLessonById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getAllLessons() {
        List<LessonResponse> responses = lessonService.getAllLessons();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Lesson deleted successfully"));
    }
}
