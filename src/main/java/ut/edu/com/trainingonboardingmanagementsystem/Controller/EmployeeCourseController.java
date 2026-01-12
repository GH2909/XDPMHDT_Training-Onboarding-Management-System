package ut.edu.com.trainingonboardingmanagementsystem.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.ApiResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.AssignedCourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.CourseForEmployeeResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.EmployeeCourseService;

import java.util.List;

@RestController
@RequestMapping("/employee/courses")
@RequiredArgsConstructor
@Validated
public class EmployeeCourseController {

    private final EmployeeCourseService employeeCourseService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<List<AssignedCourseResponse>>> getAssignedCourses(@PathVariable Integer employeeId) {

        List<AssignedCourseResponse> courses = employeeCourseService.getAssignedCourses(employeeId);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{courseId}/{employeeId}")
    public ResponseEntity<ApiResponse<CourseForEmployeeResponse>> getCourseDetail(
            @PathVariable Integer employeeId,
            @PathVariable Integer courseId) {

        CourseForEmployeeResponse response = employeeCourseService.getCourseDetail(employeeId, courseId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
