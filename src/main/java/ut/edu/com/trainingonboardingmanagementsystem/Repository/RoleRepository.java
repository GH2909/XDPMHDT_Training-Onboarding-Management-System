package ut.edu.com.trainingonboardingmanagementsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.com.trainingonboardingmanagementsystem.Model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByRoleName(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
