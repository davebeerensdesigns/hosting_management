package nl.davebeerensdesigns.hosting_management.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInputDto {

    private Long id;
    private Long clientId;
    private String clientName;

}
