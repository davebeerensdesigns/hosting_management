package nl.davebeerensdesigns.hosting_management.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import nl.davebeerensdesigns.hosting_management.controller.ClientController;
import nl.davebeerensdesigns.hosting_management.dto.ClientDto;
import nl.davebeerensdesigns.hosting_management.model.Client;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

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
                .clientMetaData(entity.getClientMetaData())
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