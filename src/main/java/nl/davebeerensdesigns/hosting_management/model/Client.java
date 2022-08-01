package nl.davebeerensdesigns.hosting_management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "client")
public class Client  extends RepresentationModel<Client> {

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

    @OneToMany(mappedBy = "client")
    private Set<ClientMeta> clientMetas;

    public Map<String, String> getClientMetaData(){
        Map<String, String> metaDataMap = new HashMap<>();
        if(clientMetas != null) {
            for (ClientMeta metaData : clientMetas) {
                switch (metaData.getMetaKey()) {
                    case "_client_meta_phone" -> metaDataMap.put("phone", metaData.getMetaValue());
                    case "_client_meta_email" -> metaDataMap.put("email", metaData.getMetaValue());
                    case "_client_meta_company" -> metaDataMap.put("company", metaData.getMetaValue());
                    case "_client_meta_address" -> metaDataMap.put("address", metaData.getMetaValue());
                    case "_client_meta_city" -> metaDataMap.put("city", metaData.getMetaValue());
                    case "_client_meta_state" -> metaDataMap.put("state", metaData.getMetaValue());
                    case "_client_meta_postcode" -> metaDataMap.put("postcode", metaData.getMetaValue());
                    case "_client_meta_country" -> metaDataMap.put("country", metaData.getMetaValue());
                }
            }
        }
        return metaDataMap;
    }

    public Set<ClientMeta> getClientMetas() { return clientMetas; }
    public void addClientMeta(ClientMeta clientMeta) {
        this.clientMetas.add(clientMeta);
    }
    public void removeClientMeta(ClientMeta clientMeta) {
        this.clientMetas.remove(clientMeta);
    }
}
