package nl.davebeerensdesigns.hosting_management.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import nl.davebeerensdesigns.hosting_management.assembler.ClientDtoAssembler;
import nl.davebeerensdesigns.hosting_management.dto.ClientDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientInputDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientMetaDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientMetaInputDto;
import nl.davebeerensdesigns.hosting_management.model.Client;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import nl.davebeerensdesigns.hosting_management.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/hm/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientDtoAssembler clientDtoAssembler;

    @Autowired
    public ClientController(ClientService clientService, ClientDtoAssembler clientDtoAssembler) {
        this.clientService = clientService;
        this.clientDtoAssembler = clientDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ClientDto>> getClients(){
        return ResponseEntity.ok(clientDtoAssembler.toCollectionModel(clientService.getClients()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") Long id)  {
        return clientService.getClient(id) //
                .map(client -> {
                    ClientDto clientDto = clientDtoAssembler.toModel(client)
                            .add(linkTo(methodOn(ClientController.class).getClients()).withRel("clients"));

                    return ResponseEntity.ok(clientDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addClient(@RequestBody @Valid ClientInputDto dto) {
        try {
            Client savedClient = clientService.addClient(dto);

            ClientDto clientDto = clientDtoAssembler.toModel(savedClient)
                    .add(linkTo(methodOn(ClientController.class).getClients()).withRel("clients"));

            return ResponseEntity //
                    .created(new URI(clientDto.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(clientDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create client");
        }
    }

    @PutMapping(value = "/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable("clientId") Long id, @RequestBody @Valid ClientInputDto dto) {
        Client updatedClient = clientService.updateClient(id, dto);
        ClientDto clientDto = clientDtoAssembler.toModel(updatedClient)
                .add(linkTo(methodOn(ClientController.class).getClients()).withRel("clients"));

        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{clientId}/metadata")
    public ResponseEntity<?> addClientMeta(@PathVariable("clientId") Long id, @RequestBody @Valid ClientMetaInputDto dto) {
        Client newClientMeta = clientService.addClientMeta(id, dto);
        ClientDto clientDto = clientDtoAssembler.toModel(newClientMeta)
                .add(linkTo(methodOn(ClientController.class).getClients()).withRel("clients"));

        return ResponseEntity.ok(clientDto);
    }

    @PutMapping(value = "/{clientId}/metadata")
    public ResponseEntity<?> updateClientMeta(@PathVariable("clientId") Long id, @RequestBody @Valid ClientMetaInputDto dto) {
        Client newClientMeta = clientService.updateClientMeta(id, dto);
        ClientDto clientDto = clientDtoAssembler.toModel(newClientMeta)
                .add(linkTo(methodOn(ClientController.class).getClients()).withRel("clients"));

        return ResponseEntity.ok(clientDto);
    }
}
