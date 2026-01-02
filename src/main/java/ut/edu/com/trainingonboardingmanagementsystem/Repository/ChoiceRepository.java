package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
}
