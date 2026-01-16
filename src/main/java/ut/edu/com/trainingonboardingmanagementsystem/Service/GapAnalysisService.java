package ut.edu.com.trainingonboardingmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.GapAnalysisRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.GapAnalysisResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.CompetencyList;
import ut.edu.com.trainingonboardingmanagementsystem.Model.GapAnalysis;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CompetencyListRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.GapAnalysisRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GapAnalysisService {
    private final GapAnalysisRepository gapAnalysisRepo;
    private final UserRepository userRepo;
    private final CompetencyListRepository competencyListRepo;

    @Transactional
    public GapAnalysis create(GapAnalysisRequest req) {
        User employee = userRepo.findById(req.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với ID: " + req.getEmployeeId()));

        CompetencyList competency = competencyListRepo.findById(req.getCompetencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Năng lực không tồn tại với ID: " + req.getCompetencyId()));

        GapAnalysis gapAnalysis = GapAnalysis.builder()
                .employee(employee)
                .competency(competency)
                .requiredLevel(req.getRequiredLevel())
                .currentLevel(req.getCurrentLevel())
                .build();

        return gapAnalysisRepo.save(gapAnalysis);
    }

    public List<GapAnalysisResponse> getAll() {
        return gapAnalysisRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<GapAnalysisResponse> getByEmployeeId(Integer employeeId) {
        return gapAnalysisRepo.findByEmployeeId(employeeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public GapAnalysisResponse getById(Integer id) {
        GapAnalysis gapAnalysis = gapAnalysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gap Analysis không tồn tại với ID: " + id));
        return mapToResponse(gapAnalysis);
    }

    @Transactional
    public GapAnalysis update(Integer id, GapAnalysisRequest req) {
        GapAnalysis gapAnalysis = gapAnalysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gap Analysis không tồn tại với ID: " + id));

        if (req.getEmployeeId() != null) {
            User employee = userRepo.findById(req.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với ID: " + req.getEmployeeId()));
            gapAnalysis.setEmployee(employee);
        }

        if (req.getCompetencyId() != null) {
            CompetencyList competency = competencyListRepo.findById(req.getCompetencyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Năng lực không tồn tại với ID: " + req.getCompetencyId()));
            gapAnalysis.setCompetency(competency);
        }

        if (req.getRequiredLevel() != null) {
            gapAnalysis.setRequiredLevel(req.getRequiredLevel());
        }

        if (req.getCurrentLevel() != null) {
            gapAnalysis.setCurrentLevel(req.getCurrentLevel());
        }

        return gapAnalysisRepo.save(gapAnalysis);
    }

    @Transactional
    public void delete(Integer id) {
        GapAnalysis gapAnalysis = gapAnalysisRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gap Analysis không tồn tại với ID: " + id));
        gapAnalysisRepo.delete(gapAnalysis);
    }

    private GapAnalysisResponse mapToResponse(GapAnalysis gapAnalysis) {
        return GapAnalysisResponse.builder()
                .id(gapAnalysis.getId())
                .employeeId(gapAnalysis.getEmployee().getId())
                .employeeName(gapAnalysis.getEmployee().getFullName())
                .competencyId(gapAnalysis.getCompetency().getId())
                .competencyName(gapAnalysis.getCompetency().getName())
                .requiredLevel(gapAnalysis.getRequiredLevel())
                .currentLevel(gapAnalysis.getCurrentLevel())
                .gapValue(gapAnalysis.getGapValue())
                .build();
    }
}

