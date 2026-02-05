package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseCreateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CourseUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.CourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/hr/course")
@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseCreateRequest req) {
        return ResponseEntity.ok(courseService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'TRAINER')")
    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CourseUpdateRequest req){
        return ResponseEntity.ok(courseService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        courseService.delete(id);
        return ResponseEntity.ok("Khoá học đã được xóa");
    }
}
