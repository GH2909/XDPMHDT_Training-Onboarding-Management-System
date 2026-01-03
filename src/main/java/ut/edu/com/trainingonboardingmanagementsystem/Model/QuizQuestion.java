package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "quiz_question")
@Getter
@Setter
@Data
@NoArgsConstructor
@IdClass(QuizQuestionId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizQuestion {

    @Id
    @Column(name = "quiz_id")
    private Integer quizId;

    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;
}

