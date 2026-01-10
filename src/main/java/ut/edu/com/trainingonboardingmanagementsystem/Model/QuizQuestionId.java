package ut.edu.com.trainingonboardingmanagementsystem.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable //nhúng làm khóa chính
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QuizQuestionId implements Serializable {

    private Integer quizId;
    private Integer questionId;
}
