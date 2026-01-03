package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Lesson;

import java.util.List;
import java.util.Optional;
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourseId(Integer courseId);
    Optional<Lesson> findByIdAndCreatedBy(Integer id, Integer createdBy);
}
