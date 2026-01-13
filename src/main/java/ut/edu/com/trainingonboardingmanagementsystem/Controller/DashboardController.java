package ut.edu.com.trainingonboardingmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LearningProgressRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hr/dashboard")
@PreAuthorize("hasRole('HR')")
@RequiredArgsConstructor
public class DashboardController {
    private final LearningProgressRepository repo;
    @GetMapping
    public Map<String, Long> dashboard() {
        Map<String, Long> m = new HashMap<>();
        m.put("studying", repo.countByStatus(LearningStatus.STUDYING));
        m.put("passed", repo.countByStatus(LearningStatus.PASSED));
        m.put("failed", repo.countByStatus(LearningStatus.FAILED));
        return m;
    }
}
