package ut.edu.com.trainingonboardingmanagementsystem.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.AssignedCourseResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.LearningProgressResponse;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Assignment;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Course;
import ut.edu.com.trainingonboardingmanagementsystem.Model.LearningProgress;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LearningProgressRepository;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {
    private final LearningProgressRepository learningProgressRepository;

    public AssignedCourseResponse assignedCourseResponse(Assignment assignment) {
        Course course = assignment.getCourse();

        LearningProgress progress = learningProgressRepository
                .findByEmployeeIdAndCourseId(assignment.getEmployee().getId(), course.getId())
                .orElse(null);

        return AssignedCourseResponse.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .category(course.getCategory())
                .duration(course.getDuration())
                .assignedDate(assignment.getAssignedDate())
                .assignedByName(assignment.getAssignedBy() != null ?
                        assignment.getAssignedBy().getFullName() : null)
                .learningProgress(progress != null ? learningProgressResponse(progress) : null)
                .build();
    }

    public LearningProgressResponse learningProgressResponse(LearningProgress progress) {
        Course course = progress.getCourse();

        int totalLessons = course.getLessons().size();
        int totalQuizzes = course.getLessons().stream()
                .mapToInt(lesson -> lesson.getQuizzes().size())
                .sum();

        return LearningProgressResponse.builder()
                .id(progress.getId())
                .progressPercent(progress.getProgressPercent())
                .score(progress.getScore())
                .status(progress.getStatus())
                .completedLessons(0) // theo dõi trong bảng khác
                .totalLessons(totalLessons)
                .completedQuizzes(0) // theo dõi ở QuizAttempt
                .totalQuizzes(totalQuizzes)
                .build();
    }
}
