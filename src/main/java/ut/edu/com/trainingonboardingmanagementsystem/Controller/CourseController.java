package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CourseService;

@RestController
@RequestMapping("/hr/course")
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody CourseCreateRequest req) {
        try {
            var course = courseService.create(req);
            return ResponseEntity.ok(ApiResponse.success(course, "Tạo khóa học thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể tạo khóa học: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        try {
            var courses = courseService.getAll();
            return ResponseEntity.ok(ApiResponse.success(courses, "Lấy danh sách khóa học thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách khóa học: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        try {
            var course = courseService.getById(id);
            return ResponseEntity.ok(ApiResponse.success(course, "Lấy thông tin khóa học thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id, @RequestBody CourseUpdateRequest req) {
        try {
            var course = courseService.update(id, req);
            return ResponseEntity.ok(ApiResponse.success(course, "Cập nhật khóa học thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể cập nhật khóa học: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa khóa học thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}
