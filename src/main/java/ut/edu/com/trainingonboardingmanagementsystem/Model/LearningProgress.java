package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "learning_progress")
public class LearningProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    User employee;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Column(name = "progress_percent")
    Float progressPercent;

    @Column(name = "score")
    Float score;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    LearningStatus status = LearningStatus.STUDYING;

    @OneToOne(mappedBy = "learningProgress", cascade = CascadeType.ALL)
    private Certificate certificate;
}
