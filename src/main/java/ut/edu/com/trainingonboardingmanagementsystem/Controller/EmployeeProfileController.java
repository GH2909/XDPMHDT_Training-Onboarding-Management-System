package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChangePasswordRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.EmployeeProfileResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Service.UserService;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

import java.util.List;

@RestController
@RequestMapping("/employee/profile")
@RequiredArgsConstructor
@Validated
public class EmployeeProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getEmployeeProfiles() {
        List<User> userList = userService.getEmployeeProfiles();
        return ResponseEntity.ok(ApiResponse.success(userList));
    }

    @GetMapping("/{email}/{status}")
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> getProfile(
            @PathVariable String email,
            @PathVariable LearningStatus status) {

        EmployeeProfileResponse response = userService.getProfile(email, status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping(("/{email}/{status}"))
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> updateProfile(
            @PathVariable String email,
            @PathVariable LearningStatus status,
            @Valid @RequestBody EmployeeProfileUpdateRequest request) {

        EmployeeProfileResponse response = userService.updateEmployeeProfile(
                email, request, status);
        return ResponseEntity.ok(ApiResponse.success(response, "Cập nhật trang cá nhân thành công"));
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable String email,
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(email, request);
        return ResponseEntity.ok(ApiResponse.success(null, "Đổi mật khẩu thành công."));
    }
}
