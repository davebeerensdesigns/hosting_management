package nl.davebeerensdesigns.hosting_management.repository;

import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientWebsiteRepository extends JpaRepository<ClientWebsite, Long> {
    List<ClientWebsite> findAllByClientId(Long clientId);
}
