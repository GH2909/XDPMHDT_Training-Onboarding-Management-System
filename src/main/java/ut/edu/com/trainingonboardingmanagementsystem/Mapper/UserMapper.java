package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileCreationRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.EmployeeProfileResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.EmployeeStatisticsResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.LearningProgress;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.AssignmentRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LearningProgressRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizAttemptRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final LearningProgressRepository learningProgressRepository;
    private final AssignmentRepository assignmentRepository;
//    private final QuizAttemptRepository quizAttemptRepository;

    public EmployeeProfileResponse getEmployeeProfile(User employee) {
//        EmployeeStatisticsResponse statistics = buildStatistics(employee.getId(),status);

        return EmployeeProfileResponse.builder()
                .id(employee.getId())
                .userName(employee.getUserName())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .phone(employee.getPhone())
                .avatar(employee.getAvatar())
                .status(employee.getStatus())
                .roleName(employee.getRole() != null ? employee.getRole().getRoleName() : null)
                .createdAt(employee.getCreatedAt())
//                .statistics(statistics)
                .build();
    }

    //    private EmployeeStatisticsResponse buildStatistics(Integer eId, LearningStatus status) {
//        Long totalAssigned = (long) assignmentRepository.findByEmployeeIdWithCourse(eId).size();
//        Long completed = learningProgressRepository.countByStatus(status);
//
//        List<LearningProgress> progresses = learningProgressRepository.findByEmployeeId(eId);
//        Long inProgress = progresses.stream()
//                .filter(lp -> lp.getStatus() == LearningStatus.STUDYING)
//                .count();
//
//        Float avgScore = progresses.isEmpty() ? 0f :
//                (float) progresses.stream()
//                        .mapToDouble(LearningProgress::getScore)
//                        .average()
//                        .orElse(0.0);
//
//        // Quiz statistics would need QuizAttempt data
//
//        return EmployeeStatisticsResponse.builder()
//                .totalAssignedCourses(totalAssigned)
//                .completedCourses(completed)
//                .inProgressCourses(inProgress)
//                .averageScore(avgScore)
//                .totalQuizzesTaken(0) // Calculate from QuizAttempt
//                .totalQuizzesPassed(0) // Calculate from QuizAttempt
//                .build();
//    }
//
    public void updateEmployeeProfile(User employee, EmployeeProfileUpdateRequest request) {
        employee.setFullName(request.getFullName());
        employee.setPhone(request.getPhone());
        if (request.getAvatar() != null) {
            employee.setAvatar(request.getAvatar());
        }
    }
}

