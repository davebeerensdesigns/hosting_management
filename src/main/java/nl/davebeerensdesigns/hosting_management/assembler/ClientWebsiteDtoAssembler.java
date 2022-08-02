package nl.davebeerensdesigns.hosting_management.assembler;

import nl.davebeerensdesigns.hosting_management.controller.ClientController;
import nl.davebeerensdesigns.hosting_management.controller.ClientWebsiteController;
import nl.davebeerensdesigns.hosting_management.dto.ClientWebsiteDto;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientWebsiteDtoAssembler implements RepresentationModelAssembler<ClientWebsite, ClientWebsiteDto> {

    @Override
    public ClientWebsiteDto toModel(ClientWebsite entity) {
        ClientWebsiteDto clientWebsiteDto = ClientWebsiteDto.builder()
                .id(entity.getId())
                .serverAddress(entity.getServerAddress())
                .clientPackage(entity.getClientPackage())
                .clientDomain(entity.getClientDomain())
                .sslType(entity.getSslType())
                .sslStatus(entity.getSslStatus())
                .sslExpires(entity.getSslExpires())
                .clientId(entity.getClient().getId())
                .build();

        clientWebsiteDto.add(linkTo(methodOn(ClientWebsiteController.class).getClientWebsite(clientWebsiteDto.getId())).withSelfRel());
        clientWebsiteDto.add(linkTo(methodOn(ClientController.class).getClient(clientWebsiteDto.getClientId())).withSelfRel());

        return clientWebsiteDto;
    }

    @Override
    public CollectionModel<ClientWebsiteDto> toCollectionModel(Iterable<? extends ClientWebsite> entities) {
        CollectionModel<ClientWebsiteDto> clientWebsiteDto = RepresentationModelAssembler.super.toCollectionModel(entities);

        clientWebsiteDto.add(linkTo(methodOn(ClientWebsiteController.class).getClientWebsites()).withSelfRel());

        return clientWebsiteDto;
    }
}
