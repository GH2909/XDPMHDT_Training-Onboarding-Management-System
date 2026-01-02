package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Security.User.UserPrincipal;
import ut.edu.com.trainingonboardingmanagementsystem.Service.AssignmentService;


@RestController
@RequestMapping("/hr/assignments")
@PreAuthorize("hasRole('HR')")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService service;
    @PostMapping("/assign")
    public ResponseEntity<?> assign(
            @RequestBody AssignRequest req,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        service.assign(req, user.getId());
        return ResponseEntity.ok("OK");
    }
    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal UserPrincipal user) {
        return user;
    }

}
