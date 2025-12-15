package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChangePasswordRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileCreationRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/employeeProfile")
public class EmployeeProfileController {

    @Autowired
    UserService userService;

    @PostMapping
    ApiResponse<User> createEmployeeProfile(@RequestBody EmployeeProfileCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.creatEmployeeProfile(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<User>> getEmployeeProfiles() {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getEmployeeProfiles());
        return apiResponse;
    }

    @GetMapping("/{email}")
    ApiResponse<User> getEmployeeProfiles(@PathVariable String email) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getEmployeeProfile(email));
        return apiResponse;
    }

    @PutMapping("/{email}")
    ApiResponse<User> updateEmployeeProfile(
            @PathVariable String email,
            @RequestBody EmployeeProfileUpdateRequest request) {

        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateEmployeeProfile(email, request));
        return apiResponse;
    }

    @PutMapping("/{email}/changePassword")
    ApiResponse<String> changePassword(
            @PathVariable String email,
            @RequestBody ChangePasswordRequest request) {

        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.changePassword(email, request);
        apiResponse.setResult("Password changed successfully");
        return apiResponse;
    }

    @DeleteMapping("/{email}")
    ApiResponse<String> deleteEmployeeProfile(@PathVariable String email){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Customerprofile has been deleted");
        userService.deleteEmployeeProfile(email);
        return apiResponse;
    }

}
