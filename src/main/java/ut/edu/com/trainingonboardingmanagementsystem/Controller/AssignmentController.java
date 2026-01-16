package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignCourseRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Security.User.UserPrincipal;
import ut.edu.com.trainingonboardingmanagementsystem.Service.AssignmentService;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CompetencyFrameworkService;
import ut.edu.com.trainingonboardingmanagementsystem.Service.GapAnalysisService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hr/assignments")
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService service;
    private final CompetencyFrameworkService frameworkService;
    private final GapAnalysisService gapAnalysisService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> assign(
            @RequestBody AssignCourseRequest req,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        try {
            var assignment = service.assign(req, user.getId());
            return ResponseEntity.ok(ApiResponse.success(assignment, "Gán khóa học thành công. Thông báo đã được gửi tới nhân viên."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.CONFLICT.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể gán khóa học: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        try {
            var assignments = service.getAll();
            return ResponseEntity.ok(ApiResponse.success(assignments, "Lấy danh sách phân công thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách phân công: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        try {
            var assignment = service.getById(id);
            return ResponseEntity.ok(ApiResponse.success(assignment, "Lấy thông tin phân công thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<?>> getByEmployeeId(@PathVariable Integer employeeId) {
        try {
            var assignments = service.getByEmployeeId(employeeId);
            return ResponseEntity.ok(ApiResponse.success(assignments, "Lấy danh sách phân công của nhân viên thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách phân công: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa phân công thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping("/employee/{employeeId}/course/{courseId}")
    public ResponseEntity<ApiResponse<?>> deleteByEmployeeAndCourse(
            @PathVariable Integer employeeId,
            @PathVariable Integer courseId
    ) {
        try {
            service.deleteByEmployeeAndCourse(employeeId, courseId);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa phân công thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/assignment-support")
    public ResponseEntity<ApiResponse<?>> getAssignmentSupport() {
        try {
            Map<String, Object> supportData = new HashMap<>();
            supportData.put("competencyFrameworks", frameworkService.getAll());
            return ResponseEntity.ok(ApiResponse.success(supportData, "Lấy dữ liệu hỗ trợ gán khóa học thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy dữ liệu hỗ trợ: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/assignment-support/employee/{employeeId}")
    public ResponseEntity<ApiResponse<?>> getAssignmentSupportForEmployee(@PathVariable Integer employeeId) {
        try {
            Map<String, Object> supportData = new HashMap<>();
            supportData.put("competencyFrameworks", frameworkService.getAll());
            supportData.put("gapAnalysis", gapAnalysisService.getByEmployeeId(employeeId));
            return ResponseEntity.ok(ApiResponse.success(supportData, "Lấy dữ liệu hỗ trợ gán khóa học cho nhân viên thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy dữ liệu hỗ trợ: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/debug-auth")
    public ResponseEntity<ApiResponse<?>> debugAuth(@AuthenticationPrincipal UserPrincipal user) {
        try {
            Map<String, Object> debugInfo = new HashMap<>();
            if (user != null) {
                debugInfo.put("userId", user.getId());
                debugInfo.put("email", user.getUsername());
                debugInfo.put("authorities", user.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .toList());
                debugInfo.put("isAuthenticated", true);
            } else {
                debugInfo.put("isAuthenticated", false);
                debugInfo.put("message", "User not authenticated");
            }
            return ResponseEntity.ok(ApiResponse.success(debugInfo, "Debug authentication thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Không thể xác thực: " + e.getMessage(),
                            HttpStatus.UNAUTHORIZED.value()));
        }
    }
}
