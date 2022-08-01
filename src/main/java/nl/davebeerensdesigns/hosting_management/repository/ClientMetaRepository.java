package nl.davebeerensdesigns.hosting_management.repository;

import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientMetaRepository extends JpaRepository<ClientMeta, Long> {
    List<ClientMeta> findByClientId(Long clientId);
    ClientMeta findByMetaKeyAndClientId(String metaKey, Long clientId);
    Long deleteAllById(Long clientId);
    Long deleteByMetaKeyAndClientId(String metaKey, Long clientId);
}
