package nl.davebeerensdesigns.hosting_management.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import nl.davebeerensdesigns.hosting_management.controller.ClientController;
import nl.davebeerensdesigns.hosting_management.dto.ClientDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientMetaDto;
import nl.davebeerensdesigns.hosting_management.dto.ClientWebsiteDto;
import nl.davebeerensdesigns.hosting_management.model.Client;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ClientDtoAssembler implements RepresentationModelAssembler<Client, ClientDto> {

    @Override
    public ClientDto toModel(Client entity) {
        ClientDto clientDto = ClientDto.builder()
                .id(entity.getId())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .clientMeta(entity.getClientMeta())
                .clientWebsite(entity.getClientWebsite())
                .build();

        clientDto.add(linkTo(methodOn(ClientController.class).getClient(clientDto.getId())).withSelfRel());

        return clientDto;
    }

    @Override
    public CollectionModel<ClientDto> toCollectionModel(Iterable<? extends Client> entities) {
        CollectionModel<ClientDto> clientDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        clientDto.add(linkTo(methodOn(ClientController.class).getClients()).withSelfRel());

        return clientDto;
    }
}