package nl.davebeerensdesigns.hosting_management.service;

import nl.davebeerensdesigns.hosting_management.exception.EntityNotFoundException;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import nl.davebeerensdesigns.hosting_management.repository.ClientWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientWebsiteService {
    private final ClientWebsiteRepository clientWebsiteRepository;

    @Autowired
    public ClientWebsiteService(ClientWebsiteRepository clientWebsiteRepository) {
        this.clientWebsiteRepository = clientWebsiteRepository;
    }

    public List<ClientWebsite> getClientWebsites(){
        List<ClientWebsite> clientWebsites = clientWebsiteRepository.findAll();
        if (clientWebsites.isEmpty()) {
            throw new EntityNotFoundException(ClientWebsite.class);
        }
        return clientWebsites;
    }

    public Optional<ClientWebsite> getClientWebsite(Long id){
        Optional<ClientWebsite> clientWebsite = clientWebsiteRepository.findById(id);
        if (clientWebsite.isEmpty()) {
            throw new EntityNotFoundException(ClientWebsite.class, "id", id.toString());
        }
        return clientWebsite;
    }
}
