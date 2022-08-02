package nl.davebeerensdesigns.hosting_management.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "client_meta", collectionRelation = "client_metas")
public class ClientMetaDto extends RepresentationModel<ClientMetaDto> {
    private final Long id;
    private final String company;
    private final String address;
    private final String city;
    private final String state;
    private final String zipcode;
    private final String country;
    private final String email;
    private final String phone;
}
