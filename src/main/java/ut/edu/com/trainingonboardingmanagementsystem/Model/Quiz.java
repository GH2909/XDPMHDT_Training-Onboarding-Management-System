package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import ut.edu.com.trainingonboardingmanagementsystem.enums.QuizStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

//    @Column(name = "category_id", nullable = false)
//    private Integer categoryId;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore;

    @Column(name = "pass_score", nullable = false)
    private Integer passScore;

    @Column(name = "attempt_limit", nullable = false)
    private Integer attemptLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private QuizStatus status = QuizStatus.DRAFT;

    @JoinColumn(name = "start_time")
    private LocalDateTime startTime;

    @JoinColumn(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "show_answers")
    private Boolean showAnswers = false;

    @Column(name = "allow_review")
    private Boolean allowReview = false;

    @Column(name = "shuffle_questions")
    private Boolean shuffleQuestions = false;

    @Column(name = "shuffle_choices")
    private Boolean shuffleChoices = false;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<QuizQuestion> quizQuestions = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
