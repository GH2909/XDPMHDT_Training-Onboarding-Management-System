package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CompetencyFrameworkRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CompetencyFrameworkService;

@RestController
@RequestMapping({"/hr/competency-framework", "/hr/competency_framework"})
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class CompetencyFrameworkController {

    private final CompetencyFrameworkService frameworkService;

    // Log để debug
    static {
        System.out.println("CompetencyFrameworkController đã được load!");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody CompetencyFrameworkRequest req) {
        try {
            var framework = frameworkService.create(req);
            return ResponseEntity.ok(ApiResponse.success(framework, "Tạo khung năng lực thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.CONFLICT.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể tạo khung năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        try {
            var frameworks = frameworkService.getAll();
            return ResponseEntity.ok(ApiResponse.success(frameworks, "Lấy danh sách khung năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách khung năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        try {
            var framework = frameworkService.getById(id);
            return ResponseEntity.ok(ApiResponse.success(framework, "Lấy thông tin khung năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id, @RequestBody CompetencyFrameworkRequest req) {
        try {
            var framework = frameworkService.update(id, req);
            return ResponseEntity.ok(ApiResponse.success(framework, "Cập nhật khung năng lực thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.CONFLICT.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể cập nhật khung năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        try {
            frameworkService.delete(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa khung năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}
