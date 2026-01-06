package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "gap_analysis")
public class GapAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
     Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
     User employee;

    @ManyToOne
    @JoinColumn(name = "competency_l_id")
    CompetencyList competency;

    @Column(name = "required_level")
    Integer requiredLevel;

    @Column(name = "current_level")
    Integer currentLevel;

    @Column(name = "gap_value", insertable = false, updatable = false)
     Integer gapValue;
}
