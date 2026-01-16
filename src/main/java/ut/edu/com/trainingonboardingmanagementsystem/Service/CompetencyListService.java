package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.CompetencyListRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyFramework;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyList;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CompetencyFrameworkRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CompetencyListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetencyListService {
    private final CompetencyListRepository competencyListRepo;
    private final CompetencyFrameworkRepository frameworkRepo;

    @Transactional
    public CompetencyList create(CompetencyListRequest req) {
        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên năng lực không được để trống");
        }

        CompetencyFramework framework = frameworkRepo.findById(req.getFrameworkId())
                .orElseThrow(() -> new ResourceNotFoundException("Khung năng lực không tồn tại với ID: " + req.getFrameworkId()));

        CompetencyList competencyList = CompetencyList.builder()
                .name(req.getName())
                .framework(framework)
                .build();
        return competencyListRepo.save(competencyList);
    }

    public List<CompetencyList> getAll() {
        return competencyListRepo.findAll();
    }

    public List<CompetencyList> getByFrameworkId(Integer frameworkId) {
        return competencyListRepo.findByFrameworkId(frameworkId);
    }

    public CompetencyList getById(Integer id) {
        return competencyListRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Năng lực không tồn tại với ID: " + id));
    }

    @Transactional
    public CompetencyList update(Integer id, CompetencyListRequest req) {
        CompetencyList competencyList = competencyListRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Năng lực không tồn tại với ID: " + id));

        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            competencyList.setName(req.getName());
        }

        if (req.getFrameworkId() != null) {
            CompetencyFramework framework = frameworkRepo.findById(req.getFrameworkId())
                    .orElseThrow(() -> new ResourceNotFoundException("Khung năng lực không tồn tại với ID: " + req.getFrameworkId()));
            competencyList.setFramework(framework);
        }

        return competencyListRepo.save(competencyList);
    }

    @Transactional
    public void delete(Integer id) {
        CompetencyList competencyList = competencyListRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Năng lực không tồn tại với ID: " + id));
        competencyListRepo.delete(competencyList);
    }
}
