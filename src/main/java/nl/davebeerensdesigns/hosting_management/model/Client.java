package nl.davebeerensdesigns.hosting_management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "client")
public class Client extends RepresentationModel<Client> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @Column(nullable = false)
    @NotNull(message = "Client ID is mandatory")
    private Long clientId;

    @Column(nullable = false)
    @NotNull(message = "Client name is mandatory")
    private String clientName;

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private ClientMeta clientMeta;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<ClientWebsite> clientWebsite;
}
