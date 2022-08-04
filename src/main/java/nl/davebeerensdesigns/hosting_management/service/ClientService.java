package nl.davebeerensdesigns.hosting_management.service;

import nl.davebeerensdesigns.hosting_management.dto.ClientInputDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientMetaInputDto;
import nl.davebeerensdesigns.hosting_management.exception.EntityExistsException;
import nl.davebeerensdesigns.hosting_management.exception.EntityNotFoundException;
import nl.davebeerensdesigns.hosting_management.model.Client;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.repository.ClientRepository;
import nl.davebeerensdesigns.hosting_management.utils.HtmlToTextResolver;
import nl.davebeerensdesigns.hosting_management.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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

    public Client findClient(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Client.class, "id", id.toString()));
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

    public Client addClientMeta (Long id, ClientMetaInputDto dto) {
        Client client = findClient(id);

        ClientMeta newClientMeta = new ClientMeta();

        newClientMeta.setClient(client);

        if (dto.getCompanyPhone() != null) {
            String clientPhone = HtmlToTextResolver.HtmlToText(dto.getCompanyPhone());
            if (!ValidMetaData.isValidPhone(clientPhone)) {
                throw new IllegalArgumentException("Please enter a valid phone number");
            } else {
                newClientMeta.setCompanyPhone(clientPhone);
            }
        }

        if (dto.getCompanyEmail() != null) {
            String clientEmail = HtmlToTextResolver.HtmlToText(dto.getCompanyEmail());
            if (!ValidMetaData.isValidEmail(clientEmail)) {
                throw new IllegalArgumentException("Please enter a valid email address");
            } else {
                newClientMeta.setCompanyEmail(clientEmail);
            }
        }

        if (dto.getCompanyName() != null) {
            String clientCompany = HtmlToTextResolver.HtmlToText(dto.getCompanyName());
            if (!ValidMetaData.isValidCompany(clientCompany)) {
                throw new IllegalArgumentException("Please enter a valid company");
            } else {
                newClientMeta.setCompanyName(clientCompany);
            }
        }

        if (dto.getCompanyAddress() != null) {
            String clientAddress = HtmlToTextResolver.HtmlToText(dto.getCompanyAddress());
            if (!ValidMetaData.isValidAddress(clientAddress)) {
                throw new IllegalArgumentException("Please enter a valid address");
            } else {
                newClientMeta.setCompanyAddress(clientAddress);
            }
        }

        if (dto.getCompanyCity() != null) {
            String clientCity = HtmlToTextResolver.HtmlToText(dto.getCompanyCity());
            if (!ValidMetaData.isValidCity(clientCity)) {
                throw new IllegalArgumentException("Please enter a valid city");
            } else {
                newClientMeta.setCompanyCity(clientCity);
            }
        }

        if (dto.getCompanyState() != null) {
            String clientState = HtmlToTextResolver.HtmlToText(dto.getCompanyState());
            if (!ValidMetaData.isValidState(clientState)) {
                throw new IllegalArgumentException("Please enter a valid state");
            } else {
                newClientMeta.setCompanyState(clientState);
            }
        }

        if (dto.getCompanyZipcode() != null) {
            String clientZipcode = HtmlToTextResolver.HtmlToText(dto.getCompanyZipcode());
            if (!ValidMetaData.isValidZipcode(clientZipcode)) {
                throw new IllegalArgumentException("Please enter a valid zipcode");
            } else {
                newClientMeta.setCompanyZipcode(clientZipcode);
            }
        }

        if (dto.getCompanyCountry() != null) {
            String clientCountry = HtmlToTextResolver.HtmlToText(dto.getCompanyCountry());
            if (!ValidMetaData.isValidCountry(clientCountry)) {
                throw new IllegalArgumentException("Please enter a valid country");
            } else {
                newClientMeta.setCompanyCountry(clientCountry);
            }
        }

        client.setClientMeta(newClientMeta);

        return clientRepository.save(client);
    }

    public Client updateClientMeta (Long id, ClientMetaInputDto dto) {

        Client client = findClient(id);

        if (dto.getCompanyPhone() != null) {
            String clientPhone = HtmlToTextResolver.HtmlToText(dto.getCompanyPhone());
            if (!ValidMetaData.isValidPhone(clientPhone)) {
                throw new IllegalArgumentException("Please enter a valid phone number");
            } else {
                client.getClientMeta().setCompanyPhone(clientPhone);
            }
        }

        if (dto.getCompanyEmail() != null) {
            String clientEmail = HtmlToTextResolver.HtmlToText(dto.getCompanyEmail());
            if (!ValidMetaData.isValidEmail(clientEmail)) {
                throw new IllegalArgumentException("Please enter a valid email address");
            } else {
                client.getClientMeta().setCompanyEmail(clientEmail);
            }
        }

        if (dto.getCompanyName() != null) {
            String clientCompany = HtmlToTextResolver.HtmlToText(dto.getCompanyName());
            if (!ValidMetaData.isValidCompany(clientCompany)) {
                throw new IllegalArgumentException("Please enter a valid company");
            } else {
                client.getClientMeta().setCompanyName(clientCompany);
            }
        }

        if (dto.getCompanyAddress() != null) {
            String clientAddress = HtmlToTextResolver.HtmlToText(dto.getCompanyAddress());
            if (!ValidMetaData.isValidAddress(clientAddress)) {
                throw new IllegalArgumentException("Please enter a valid address");
            } else {
                client.getClientMeta().setCompanyAddress(clientAddress);
            }
        }

        if (dto.getCompanyCity() != null) {
            String clientCity = HtmlToTextResolver.HtmlToText(dto.getCompanyCity());
            if (!ValidMetaData.isValidCity(clientCity)) {
                throw new IllegalArgumentException("Please enter a valid city");
            } else {
                client.getClientMeta().setCompanyCity(clientCity);
            }
        }

        if (dto.getCompanyState() != null) {
            String clientState = HtmlToTextResolver.HtmlToText(dto.getCompanyState());
            if (!ValidMetaData.isValidState(clientState)) {
                throw new IllegalArgumentException("Please enter a valid state");
            } else {
                client.getClientMeta().setCompanyState(clientState);
            }
        }

        if (dto.getCompanyZipcode() != null) {
            String clientZipcode = HtmlToTextResolver.HtmlToText(dto.getCompanyZipcode());
            if (!ValidMetaData.isValidZipcode(clientZipcode)) {
                throw new IllegalArgumentException("Please enter a valid zipcode");
            } else {
                client.getClientMeta().setCompanyZipcode(clientZipcode);
            }
        }

        if (dto.getCompanyCountry() != null) {
            String clientCountry = HtmlToTextResolver.HtmlToText(dto.getCompanyCountry());
            if (!ValidMetaData.isValidCountry(clientCountry)) {
                throw new IllegalArgumentException("Please enter a valid country");
            } else {
                client.getClientMeta().setCompanyCountry(clientCountry);
            }
        }

        return clientRepository.save(client);
    }

}

