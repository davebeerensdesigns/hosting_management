package nl.davebeerensdesigns.hosting_management.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.davebeerensdesigns.hosting_management.model.ClientMeta;
import nl.davebeerensdesigns.hosting_management.model.ClientWebsite;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.*;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "client", collectionRelation = "clients")
public class ClientDto extends RepresentationModel<ClientDto> {

    private final Long id;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final Long clientId;
    private final String clientName;
    private final ClientMeta clientMeta;
    private final List<ClientWebsite> clientWebsite;
}