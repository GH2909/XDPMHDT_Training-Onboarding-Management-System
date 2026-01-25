package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignCourseRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.AssignedCourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.CourseForEmployeeResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Security.User.UserPrincipal;
import ut.edu.com.trainingonboardingmanagementsystem.Service.AssignmentService;
import ut.edu.com.trainingonboardingmanagementsystem.Service.UserService;

import java.util.List;


@RestController
@RequestMapping("/hr/assignments")
@PreAuthorize("hasRole('HR')")
@RequiredArgsConstructor
public class AssignmentController {
    private final UserService userService;
    private final AssignmentService service;
    @PostMapping
    public ResponseEntity<?> assign(
            @RequestBody AssignCourseRequest req,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        service.assign(req, user.getId());
        return ResponseEntity.ok("Khóa học đã được gán");
    }
    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal UserPrincipal user) {
        return user;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<List<AssignedCourseResponse>>> getAssignedCourses(@PathVariable Integer employeeId) {

        List<AssignedCourseResponse> courses = service.getAssignedCourses(employeeId);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{courseId}/{employeeId}")
    public ResponseEntity<ApiResponse<CourseForEmployeeResponse>> getCourseDetail(
            @PathVariable Integer employeeId,
            @PathVariable Integer courseId) {

        CourseForEmployeeResponse response = service.getCourseDetail(employeeId, courseId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

