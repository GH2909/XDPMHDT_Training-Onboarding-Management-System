//package ut.edu.com.trainingonboardingmanagementsystem.Model;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.FieldDefaults;
//
//@Entity
//@Table(name = "quiz_question")
//@Getter
//@Setter
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class QuizQuestion {
//
//    @EmbeddedId //quiz_id và question_id vừa là FK, vừa là PK
//    private QuizQuestionId id; //Tạo khoá chính phức hợp
//
//    @ManyToOne(fetch = FetchType.LAZY)
////    @MapsId("quizId")
//    @JoinColumn(name = "quiz_id")
//    private Quiz quiz;
//
//    @ManyToOne(fetch = FetchType.LAZY)
////    @MapsId("questionId")
//    @JoinColumn(name = "question_id")
//    private Question question;
//
//    @Column(name = "sequence_number")
//    private Integer sequenceNumber;
//}
//
