package ut.edu.com.trainingonboardingmanagementsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Request.AssignCourseRequest;
import ut.edu.com.trainingonboardingmanagementsystem.Dto.Response.*;
import ut.edu.com.trainingonboardingmanagementsystem.Exception.ResourceNotFoundException;
import ut.edu.com.trainingonboardingmanagementsystem.Mapper.AssignmentMapper;
import ut.edu.com.trainingonboardingmanagementsystem.Model.*;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Module;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.AssignmentRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.CourseRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.LearningProgressRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Repository.UserRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Validators.EmployeeValidator;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final UserRepository userRepo;

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final LearningProgressRepository learningProgressRepository;
    private final AssignmentMapper assignmentMapper;
    private final EmployeeValidator employeeValidator;

    @Transactional
    public void assign(AssignCourseRequest req, Integer hrId) {

        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));

        for (Integer empId : req.getEmployeeIds()) {

            if (assignmentRepository.existsByEmployeeIdAndCourseId(empId, course.getId())) {
                throw new RuntimeException("Nhân viên đã được gán khóa học");
            }

            Assignment a = new Assignment();
            a.setCourse(course);
            a.setEmployee(userRepo.findById(empId).orElseThrow());
            a.setAssignedBy(userRepo.findById(hrId).orElseThrow());
            a.setAssignedDate(LocalDate.now());

            assignmentRepository.save(a);
        }
    }

    public List<AssignedCourseResponse> getAssignedCourses(Integer employeeId) {
        List<Assignment> assignments = assignmentRepository.findByEmployeeIdWithCourse(employeeId);

        return assignments.stream()
                .map(assignmentMapper::assignedCourseResponse)
                .collect(Collectors.toList());
    }


    public CourseForEmployeeResponse getCourseDetail(Integer employeeId, Integer courseId) {
        employeeValidator.validateCourseAccess(employeeId, courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        LearningProgress progress = learningProgressRepository
                .findByEmployeeIdAndCourseId(employeeId, courseId)
                .orElseGet(() -> createInitialProgress(employeeId, courseId));

        return buildCourseDetailResponse(course, progress);
    }

    private LearningProgress createInitialProgress(Integer employeeId, Integer courseId) {
        User employee = new User();
        employee.setId(employeeId);

        Course course = new Course();
        course.setId(courseId);

        LearningProgress progress = LearningProgress.builder()
                .employee(employee)
                .course(course)
                .progressPercent(0f)
                .score(0f)
                .status(LearningStatus.STUDYING)
                .build();

        return learningProgressRepository.save(progress);
    }

    private CourseForEmployeeResponse buildCourseDetailResponse(
            Course course, LearningProgress progress) {

        List<LessonForEmployeeResponse> lessonResponses = course.getLessons().stream()
                .map(this::toLessonForEmployeeResponse)
                .collect(Collectors.toList());

        List<ModuleResponse> moduleResponses = course.getModules().stream()
                .map(this::toModuleResponse)
                .collect(Collectors.toList());

        return CourseForEmployeeResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .category(course.getCategory())
                .duration(course.getDuration())
                .completionRule(course.getCompletionRule())
                .lessons(lessonResponses)
                .modules(moduleResponses)
                .learningProgress(assignmentMapper.learningProgressResponse(progress))
                .build();
    }

    private LessonForEmployeeResponse toLessonForEmployeeResponse(Lesson lesson) {
        List<QuizForEmployeeResponse> quizResponses = lesson.getQuizzes().stream()
                .map(this::toQuizForEmployeeResponse)
                .collect(Collectors.toList());

        return LessonForEmployeeResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .duration(lesson.getDuration())
                .description(lesson.getDescription())
                .completed(false) // Would track in separate completion table
                .quizzes(quizResponses)
                .build();
    }

    private QuizForEmployeeResponse toQuizForEmployeeResponse(Quiz quiz) {
        return QuizForEmployeeResponse.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .durationMinutes(quiz.getDurationMinutes())
                .maxScore(quiz.getMaxScore())
                .passScore(quiz.getPassScore())
                .attemptLimit(quiz.getAttemptLimit())
                .status(quiz.getStatus())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .allowReview(quiz.getAllowReview())
                .totalQuestions(quiz.getQuizQuestions().size())
                .build();
    }

    private ModuleResponse toModuleResponse(Module module) {
        return ModuleResponse.builder()
                .id(module.getId())
                .type(module.getType())
                .url(module.getUrl())
                .build();
    }
}
