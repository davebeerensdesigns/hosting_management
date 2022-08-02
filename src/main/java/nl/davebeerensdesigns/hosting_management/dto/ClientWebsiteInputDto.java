package nl.davebeerensdesigns.hosting_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.davebeerensdesigns.hosting_management.model.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClientWebsiteInputDto {

    private Long clientId;
    private String serverAddress;
    private String clientPackage;
    private String clientDomain;
    private String sslType;
    private Boolean sslStatus;
    private Date sslExpires;
}
