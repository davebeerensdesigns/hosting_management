package nl.davebeerensdesigns.hosting_management.repository;

import nl.davebeerensdesigns.hosting_management.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsById(Long id);
    boolean existsByClientId(Long clientId);
    boolean existsByClientName(String clientName);
}
