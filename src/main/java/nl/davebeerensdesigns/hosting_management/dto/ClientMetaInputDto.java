package nl.davebeerensdesigns.hosting_management.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClientMetaInputDto {

    private Long clientId;
    private String companyName;
    private String companyAddress;
    private String companyCity;
    private String companyState;
    private String companyZipcode;
    private String companyCountry;
    private String companyEmail;
    private String companyPhone;

}
