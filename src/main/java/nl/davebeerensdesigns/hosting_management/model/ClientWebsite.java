package nl.davebeerensdesigns.hosting_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "client_website")
public class ClientWebsite extends RepresentationModel<ClientWebsite> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "server_address")
    @NotNull(message = "Server address is mandatory")
    private String serverAddress;

    @Column(name = "client_package")
    @NotNull(message = "Package name is mandatory")
    private String clientPackage;

    @Column(name = "client_domain")
    @NotNull(message = "Domain name is mandatory")
    private String clientDomain;

    @Column(name = "ssl_type")
    @NotNull(message = "SSL type is mandatory")
    private String sslType;

    @Column(name = "ssl_status")
    @NotNull(message = "Must be true or false")
    private Boolean sslStatus;

    @Column(name = "ssl_expires")
    @NotNull(message = "Expiration date is mandatory")
    private Date sslExpires;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Client is mandatory")
    @JsonIgnore
    private Client client;
}
