package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CompetencyFrameworkRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.DuplicateResourceException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyFramework;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CompetencyFrameworkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetencyFrameworkService {
    private final CompetencyFrameworkRepository frameworkRepo;

    @Transactional
    public CompetencyFramework create(CompetencyFrameworkRequest req) {
        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khung năng lực không được để trống");
        }

        if (frameworkRepo.existsByName(req.getName())) {
            throw new DuplicateResourceException("Khung năng lực với tên này đã tồn tại");
        }

        CompetencyFramework framework = CompetencyFramework.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
        return frameworkRepo.save(framework);
    }

    public List<CompetencyFramework> getAll() {
        return frameworkRepo.findAll();
    }

    public CompetencyFramework getById(Integer id) {
        return frameworkRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khung năng lực không tồn tại với ID: " + id));
    }

    @Transactional
    public CompetencyFramework update(Integer id, CompetencyFrameworkRequest req) {
        CompetencyFramework framework = frameworkRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khung năng lực không tồn tại với ID: " + id));

        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khung năng lực không được để trống");
        }

        // Check for duplicate name (excluding current framework)
        if (frameworkRepo.existsByName(req.getName()) && !framework.getName().equals(req.getName())) {
            throw new DuplicateResourceException("Khung năng lực với tên này đã tồn tại");
        }

        framework.setName(req.getName());
        if (req.getDescription() != null) {
            framework.setDescription(req.getDescription());
        }
        return frameworkRepo.save(framework);
    }

    @Transactional
    public void delete(Integer id) {
        CompetencyFramework framework = frameworkRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khung năng lực không tồn tại với ID: " + id));
        frameworkRepo.delete(framework);
    }
}
