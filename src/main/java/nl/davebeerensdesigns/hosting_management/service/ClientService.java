package nl.davebeerensdesigns.hosting_management.service;

import nl.davebeerensdesigns.hosting_management.dto.ClientInputDto;
import nl.davebeerensdesigns.hosting_management.exception.EntityExistsException;
import nl.davebeerensdesigns.hosting_management.exception.EntityNotFoundException;
import nl.davebeerensdesigns.hosting_management.model.Client;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.repository.ClientMetaRepository;
import nl.davebeerensdesigns.hosting_management.repository.ClientRepository;
import nl.davebeerensdesigns.hosting_management.utils.HtmlToTextResolver;
import nl.davebeerensdesigns.hosting_management.validators.ValidMetaData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMetaRepository clientMetaRepository;

    public ClientService(ClientRepository clientRepository, ClientMetaRepository clientMetaRepository) {
        this.clientRepository = clientRepository;
        this.clientMetaRepository = clientMetaRepository;
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

    public List<ClientMeta> getClientMetas(Long clientId) {
        List<ClientMeta> clientMetas = clientMetaRepository.findByClientId(clientId);
        if (clientMetas.isEmpty()) {
            throw new EntityNotFoundException(ClientMeta.class, "client_id", clientId.toString());
        }
        return clientMetas;
    }

    public Client addClientMeta(Long id, Map<String, String> metaData, String type) {

        Client client = clientRepository.getById(id);
        if (client == null) {
            throw new EntityNotFoundException(Client.class, "id", id.toString());
        }
        ;

        List<ClientMeta> clientMetaData = new ArrayList<>();

        for (var entry : metaData.entrySet()) {
            switch (entry.getKey()) {
                case "phone":
                    String phone = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidPhone(phone)) {
                        throw new IllegalArgumentException("Please enter a valid phone number");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_phone", phone, client));
                    }
                    break;
                case "email":
                    String email = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidEmail(email)) {
                        throw new IllegalArgumentException("Please enter a valid email address");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_email", email, client));
                    }
                    break;
                case "company":
                    String company = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidCompany(company)) {
                        throw new IllegalArgumentException("Please enter a valid company");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_company", company, client));
                    }
                    break;
                case "address":
                    String address = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidAddress(address)) {
                        throw new IllegalArgumentException("Please enter a valid address");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_address", address, client));
                    }
                    break;
                case "city":
                    String city = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidCity(city)) {
                        throw new IllegalArgumentException("Please enter a valid city");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_city", city, client));
                    }
                    break;
                case "state":
                    String state = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidState(state)) {
                        throw new IllegalArgumentException("Please enter a valid state");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_state", state, client));
                    }
                    break;
                case "postcode":
                    String postcode = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidPostcode(postcode)) {
                        throw new IllegalArgumentException("Please enter a valid postcode");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_postcode", postcode, client));
                    }
                    break;
                case "country":
                    String country = HtmlToTextResolver.HtmlToText(entry.getValue());
                    if (!ValidMetaData.isValidCountry(country)) {
                        throw new IllegalArgumentException("Please enter a valid country");
                    } else {
                        clientMetaData.add(new ClientMeta("_client_meta_country", country, client));
                    }
                    break;
            }

        }


        if (!clientMetaData.isEmpty()) {
            for (ClientMeta clientMeta : clientMetaData) {
                clientMetaRepository.save(clientMeta);
            }
        }

        return client;

    }


    public Client updateClientMeta(Long id, Map<String, String> metaData, String type) {
        if (!clientMetaRepository.existsById(id))
            throw new EntityNotFoundException(ClientMeta.class, "id", id.toString());

        for (var entry : metaData.entrySet()) {
            switch (entry.getKey()) {
                case "phone":
                    ClientMeta currentPhone = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_phone", id);
                    if (currentPhone != null) {
                        String phone = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidPhone(phone)) {
                            throw new IllegalArgumentException("Please enter a valid phone number");
                        } else {
                            currentPhone.setMetaValue(phone);
                            clientMetaRepository.save(currentPhone);
                        }
                    }
                    break;
                case "email":
                    ClientMeta currentEmail = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_email", id);
                    if (currentEmail != null) {
                        String email = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidEmail(email)) {
                            throw new IllegalArgumentException("Please enter a valid email address");
                        } else {
                            currentEmail.setMetaValue(email);
                            clientMetaRepository.save(currentEmail);
                        }
                    }
                    break;
                case "company":
                    ClientMeta currentCompany = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_company", id);
                    if (currentCompany != null) {
                        String company = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidCompany(company)) {
                            throw new IllegalArgumentException("Please enter a valid company");
                        } else {
                            currentCompany.setMetaValue(company);
                            clientMetaRepository.save(currentCompany);
                        }
                    }
                    break;
                case "address":
                    ClientMeta currentAddress = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_address", id);
                    if (currentAddress != null) {
                        String address = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidAddress(address)) {
                            throw new IllegalArgumentException("Please enter a valid address");
                        } else {
                            currentAddress.setMetaValue(address);
                            clientMetaRepository.save(currentAddress);
                        }
                    }
                    break;
                case "city":
                    ClientMeta currentCity = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_city", id);
                    if (currentCity != null) {
                        String city = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidCity(city)) {
                            throw new IllegalArgumentException("Please enter a valid city");
                        } else {
                            currentCity.setMetaValue(city);
                            clientMetaRepository.save(currentCity);
                        }
                    }
                    break;
                case "state":
                    ClientMeta currentState = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_state", id);
                    if (currentState != null) {
                        String state = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidState(state)) {
                            throw new IllegalArgumentException("Please enter a valid state");
                        } else {
                            currentState.setMetaValue(state);
                            clientMetaRepository.save(currentState);
                        }
                    }
                    break;
                case "postcode":
                    ClientMeta currentPostcode = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_postcode", id);
                    if (currentPostcode != null) {
                        String postcode = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidPostcode(postcode)) {
                            throw new IllegalArgumentException("Please enter a valid postcode");
                        } else {
                            currentPostcode.setMetaValue(postcode);
                            clientMetaRepository.save(currentPostcode);
                        }
                    }
                    break;
                case "country":
                    ClientMeta currentCountry = clientMetaRepository.findByMetaKeyAndClientId("_client_meta_country", id);
                    if (currentCountry != null) {
                        String country = HtmlToTextResolver.HtmlToText(entry.getValue());
                        if (!ValidMetaData.isValidCountry(country)) {
                            throw new IllegalArgumentException("Please enter a valid country");
                        } else {
                            currentCountry.setMetaValue(country);
                            clientMetaRepository.save(currentCountry);
                        }
                    }
                    break;
            }

        }
        return clientRepository.getById(id);
    }

    @Transactional
    public void removeClientMeta(Long id, ArrayList<String> metaKey) {
        if (!clientMetaRepository.existsById(id))
            throw new EntityNotFoundException(ClientMeta.class, "id", id.toString());

        for (String array : metaKey) {
            String formatted = HtmlToTextResolver.HtmlToText(array);
            if (ValidMetaData.isValidMetaKey(formatted)) {
                clientMetaRepository.deleteByMetaKeyAndClientId(formatted, id);
            }
        }
    }

    @Transactional
    public void removeAllClientMeta(Long id) {
        if (!clientMetaRepository.existsById(id))
            throw new EntityNotFoundException(ClientMeta.class, "id", id.toString());
        clientMetaRepository.deleteAllById(id);
    }

}

