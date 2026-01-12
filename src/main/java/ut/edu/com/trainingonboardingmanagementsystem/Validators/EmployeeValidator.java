package ut.edu.com.trainingonboardingmanagementsystem.Validators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.ChangePasswordRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.EmployeeProfileUpdateRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.AccessDeniedException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.QuizNotAvailableException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ValidationException;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Quiz;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.AssignmentRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.QuizAttemptRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;
import ut.edu.com.trainingonboardingmanagementsystem.enums.UserStatus;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmployeeValidator {
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuizAttemptRepository quizAttemptRepository;

    public void validateProfileUpdate(String email, EmployeeProfileUpdateRequest request) {
        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên."));

        if (employee.getStatus() != UserStatus.ACTIVE) {
            throw new ValidationException("Không thể cập nhật hồ sơ. Tài khoản không hoạt động.");
        }
    }

    public void validateChangePassword(String email, ChangePasswordRequest request) {
        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên."));

        // Check mk cũ
        if (!passwordEncoder.matches(request.getOldPassword(), employee.getPassword())) {
            throw new ValidationException("Mật khẩu cũ không đúng.");
        }

        // Check mk mới
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException("Mật khẩu mới đạt yêu cầu.");
        }

        // Check mk mới khác mk cũ
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new ValidationException("Mật khẩu mới phải khác mật khẩu cũ.");
        }
    }

    public void validateCourseAccess(Integer eId, Integer cId) {
        if (!assignmentRepository.existsByEmployeeIdAndCourseId(eId, cId)) {
            throw new AccessDeniedException("Bạn chưa được phân khóa học này.");
        }
    }

    public void validateQuizAttempt(Integer employeeId, Integer quizId, Quiz quiz) {
        // Check quiz status
        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new QuizNotAvailableException("Quiz is not available.");
        }

        // Check time window
        LocalDateTime now = LocalDateTime.now();
        if (quiz.getStartTime() != null && now.isBefore(quiz.getStartTime())) {
            throw new QuizNotAvailableException("Bài kiểm tra chưa bắt dầu.");
        }
        if (quiz.getEndTime() != null && now.isAfter(quiz.getEndTime())) {
            throw new QuizNotAvailableException("Bài kểm tra đã kết thúc.");
        }
    }
}
