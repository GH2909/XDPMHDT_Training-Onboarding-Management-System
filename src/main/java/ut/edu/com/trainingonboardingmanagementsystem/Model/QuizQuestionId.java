package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable //nhúng làm khóa chính
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QuizQuestionId implements Serializable {

    @Column(name = "quiz_id")
    private Integer quizId;

    @Column(name = "question_id")
    private Integer questionId;
}
