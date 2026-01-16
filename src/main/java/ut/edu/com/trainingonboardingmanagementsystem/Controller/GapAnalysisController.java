package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.GapAnalysisRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.GapAnalysisService;

@RestController
@RequestMapping({"/hr/gap-analysis", "/hr/gap_analysis"})
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class GapAnalysisController {
    private final GapAnalysisService gapAnalysisService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody GapAnalysisRequest req) {
        try {
            var gapAnalysis = gapAnalysisService.create(req);
            return ResponseEntity.ok(ApiResponse.success(gapAnalysis, "Tạo Gap Analysis thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể tạo Gap Analysis: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        try {
            var gapAnalyses = gapAnalysisService.getAll();
            return ResponseEntity.ok(ApiResponse.success(gapAnalyses, "Lấy danh sách Gap Analysis thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách Gap Analysis: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<?>> getByEmployeeId(@PathVariable Integer employeeId) {
        try {
            var gapAnalyses = gapAnalysisService.getByEmployeeId(employeeId);
            return ResponseEntity.ok(ApiResponse.success(gapAnalyses, "Lấy danh sách Gap Analysis của nhân viên thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách Gap Analysis: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        try {
            var gapAnalysis = gapAnalysisService.getById(id);
            return ResponseEntity.ok(ApiResponse.success(gapAnalysis, "Lấy thông tin Gap Analysis thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id, @RequestBody GapAnalysisRequest req) {
        try {
            var gapAnalysis = gapAnalysisService.update(id, req);
            return ResponseEntity.ok(ApiResponse.success(gapAnalysis, "Cập nhật Gap Analysis thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể cập nhật Gap Analysis: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        try {
            gapAnalysisService.delete(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa Gap Analysis thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}
