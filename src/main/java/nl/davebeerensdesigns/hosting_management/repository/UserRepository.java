package nl.davebeerensdesigns.hosting_management.repository;

import nl.davebeerensdesigns.hosting_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User getByEmail(String email);
}
