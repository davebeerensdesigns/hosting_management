package nl.davebeerensdesigns.hosting_management.service;

import nl.davebeerensdesigns.hosting_management.dto.ClientInputDto;
import nl.davebeerensdesigns.hosting_management.exception.EntityExistsException;
import nl.davebeerensdesigns.hosting_management.exception.EntityNotFoundException;
import nl.davebeerensdesigns.hosting_management.model.Client;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import nl.davebeerensdesigns.hosting_management.repository.ClientMetaRepository;
import nl.davebeerensdesigns.hosting_management.repository.ClientRepository;
import nl.davebeerensdesigns.hosting_management.repository.ClientWebsiteRepository;
import nl.davebeerensdesigns.hosting_management.utils.HtmlToTextResolver;
import nl.davebeerensdesigns.hosting_management.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMetaRepository clientMetaRepository;
    private final ClientWebsiteRepository clientWebsiteRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMetaRepository clientMetaRepository, ClientWebsiteRepository clientWebsiteRepository) {
        this.clientRepository = clientRepository;
        this.clientMetaRepository = clientMetaRepository;
        this.clientWebsiteRepository = clientWebsiteRepository;
    }

    public List<Client> getClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new EntityNotFoundException(Client.class);
        }
        return clients;
    }

    public Optional<Client> getClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new EntityNotFoundException(Client.class, "id", id.toString());
        }
        return client;
    }

    public boolean clientIdExists(Long clientId) {
        return clientRepository.existsByClientId(clientId);
    }

    public boolean clientNameExists(String clientName) {
        return clientRepository.existsByClientName(clientName);
    }

    public Client addClient(ClientInputDto client) {

        Client newClient = new Client();

        Long clientId = client.getClientId();
        if (clientIdExists(clientId)) {
            throw new EntityExistsException(Client.class, "client Id", clientId.toString());
        } else {
            newClient.setClientId(clientId);
        }

        String clientName = HtmlToTextResolver.HtmlToText(client.getClientName());
        if (clientNameExists(clientName)) {
            throw new EntityExistsException(Client.class, "client Name", clientName);
        } else {
            newClient.setClientName(clientName);
        }

        return clientRepository.save(newClient);
    }

    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId))
            throw new EntityNotFoundException(Client.class, "client Id", clientId.toString());

        clientRepository.deleteById(clientId);
    }

    public Client updateClient(Long id, ClientInputDto newClient) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Client.class, "id", id.toString()));

        if (newClient.getClientId() != null) {
            Long clientId = newClient.getClientId();
            if (!clientId.equals(client.getClientId())) {
                if (clientIdExists(clientId)) {
                    throw new EntityExistsException(Client.class, "client id", clientId.toString());
                } else {
                    client.setClientId(clientId);
                }
            }
        }

        if (newClient.getClientName() != null) {
            String clientName = newClient.getClientName();
            if (!clientName.equals(client.getClientName())) {
                if (clientNameExists(clientName)) {
                    throw new EntityExistsException(Client.class, "client name", clientName);
                } else {
                    client.setClientName(clientName);
                }
            }
        }

        return clientRepository.save(client);
    }

}

