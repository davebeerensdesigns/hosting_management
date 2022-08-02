package nl.davebeerensdesigns.hosting_management.repository;

import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientMetaRepository extends JpaRepository<ClientMeta, Long> {
    List<ClientMeta> findAllByClientId(Long clientId);
}
