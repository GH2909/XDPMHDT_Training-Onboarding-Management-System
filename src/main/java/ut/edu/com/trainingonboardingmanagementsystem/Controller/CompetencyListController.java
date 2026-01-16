package ut.edu.com.trainingonboardingmanagementsystem.Controller;

public class CompetencyListController package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
        import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CompetencyListRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CompetencyListService;

@RestController
@RequestMapping({"/hr/competency-list", "/hr/competency_list"})
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class CompetencyListController {
    private final CompetencyListService competencyListService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody CompetencyListRequest req) {
        try {
            var competencyList = competencyListService.create(req);
            return ResponseEntity.ok(ApiResponse.success(competencyList, "Tạo năng lực thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể tạo năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        try {
            var competencyLists = competencyListService.getAll();
            return ResponseEntity.ok(ApiResponse.success(competencyLists, "Lấy danh sách năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/framework/{frameworkId}")
    public ResponseEntity<ApiResponse<?>> getByFrameworkId(@PathVariable Integer frameworkId) {
        try {
            var competencyLists = competencyListService.getByFrameworkId(frameworkId);
            return ResponseEntity.ok(ApiResponse.success(competencyLists, "Lấy danh sách năng lực theo khung thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể lấy danh sách năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        try {
            var competencyList = competencyListService.getById(id);
            return ResponseEntity.ok(ApiResponse.success(competencyList, "Lấy thông tin năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id, @RequestBody CompetencyListRequest req) {
        try {
            var competencyList = competencyListService.update(id, req);
            return ResponseEntity.ok(ApiResponse.success(competencyList, "Cập nhật năng lực thành công"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Không thể cập nhật năng lực: " + e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        try {
            competencyListService.delete(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Xóa năng lực thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}
{
}
