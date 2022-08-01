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
    private Long clientName;
    private String company;
    private String address;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String email;
    private String phone;

}
