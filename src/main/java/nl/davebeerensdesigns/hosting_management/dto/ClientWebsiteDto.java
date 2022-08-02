package nl.davebeerensdesigns.hosting_management.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "client_website", collectionRelation = "client_websites")
public class ClientWebsiteDto extends RepresentationModel<ClientWebsiteDto> {
    private final Long id;
    private final String serverAddress;
    private final String clientPackage;
    private final String clientDomain;
    private final String sslType;
    private final Boolean sslStatus;
    private final Date sslExpires;
    private final Long clientId;
}
