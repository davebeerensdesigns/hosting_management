package nl.davebeerensdesigns.hosting_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "client_meta")
public class ClientMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "company_name")
    @NotNull(message = "Company name is mandatory")
    private String companyName;

    @Column(name = "company_address")
    @NotNull(message = "Company address is mandatory")
    private String companyAddress;

    @Column(name = "company_city")
    @NotNull(message = "Company city is mandatory")
    private String companyCity;

    @Column(name = "company_state")
    @NotNull(message = "Company state is mandatory")
    private String companyState;

    @Column(name = "company_zipcode")
    @NotNull(message = "Company zipcode is mandatory")
    private String companyZipcode;

    @Column(name = "company_country")
    @NotNull(message = "Company country is mandatory")
    private String companyCountry;

    @Column(name = "company_email")
    @NotNull(message = "Company email is mandatory")
    private String companyEmail;

    @Column(name = "company_phone")
    @NotNull(message = "Company phone is mandatory")
    private String companyPhone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;
}
