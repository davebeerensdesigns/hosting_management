package nl.davebeerensdesigns.hosting_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "client_meta", uniqueConstraints = @UniqueConstraint(name = "client_meta_key_unique", columnNames = {"meta_key", "client_id"}))
public class ClientMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "meta_key")
    @NotNull(message = "Metakey is mandatory")
    private String metaKey;

    @Column(name = "meta_value")
    @NotNull(message = "Metavalue is mandatory")
    private String metaValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Client is mandatory")
    private Client client;

    public ClientMeta(){}
    public ClientMeta(String metaKey, String metaValue, Client client) {
        this.metaKey = metaKey;
        this.metaValue = metaValue;
        this.client = client;
    }

}
