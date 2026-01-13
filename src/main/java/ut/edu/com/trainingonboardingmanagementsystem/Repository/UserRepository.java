package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.com.trainingonboardingmanagementsystem.Model.User;
import ut.edu.com.trainingonboardingmanagementsystem.enums.LearningStatus;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.role
        WHERE u.email = :email
    """)
    Optional<User> findByEmailWithRole(@Param("email") String email);
    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(lp) FROM LearningProgress lp WHERE lp.status = 'PASSED'")
    Long countByStatus(LearningStatus status);
}
