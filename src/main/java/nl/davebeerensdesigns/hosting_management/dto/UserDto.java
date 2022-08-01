package nl.davebeerensdesigns.hosting_management.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.davebeerensdesigns.hosting_management.model.Authority;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {

    private final String username;
    private final String email;
    private final Instant dateCreated;
    private final Instant dateModified;
    private final Boolean enabled;
    private final String apikey;
    @JsonSerialize
    private final Set<Authority> authorities;
}
