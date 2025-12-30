package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CourseService;

@RestController
@RequestMapping("/course")
//@PreAuthorize("hasRole('HR')")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseCreateRequest req) {
        return ResponseEntity.ok(courseService.create(req));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }
}
