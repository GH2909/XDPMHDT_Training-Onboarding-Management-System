package ut.edu.com.trainingonboardingmanagementsystem.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private Boolean passed;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "time_taken_minutes")
    private Integer timeTakenMinutes;
}
