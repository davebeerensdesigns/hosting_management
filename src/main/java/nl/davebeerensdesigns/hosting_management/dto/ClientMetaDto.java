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
    private final String companyName;
    private final String companyAddress;
    private final String companyCity;
    private final String companyState;
    private final String companyZipcode;
    private final String companyCountry;
    private final String companyEmail;
    private final String companyPhone;
}
