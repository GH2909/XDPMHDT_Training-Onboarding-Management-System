package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "certificate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_progress_id", unique = true)
    private LearningProgress learningProgress;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issuedDate;
}
