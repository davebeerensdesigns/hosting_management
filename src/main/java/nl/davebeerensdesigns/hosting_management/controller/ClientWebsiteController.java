package nl.davebeerensdesigns.hosting_management.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import nl.davebeerensdesigns.hosting_management.assembler.ClientWebsiteDtoAssembler;
import nl.davebeerensdesigns.hosting_management.dto.ClientWebsiteDto;
import nl.davebeerensdesigns.hosting_management.service.ClientWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/wp-json/hm/v1/websites")
public class ClientWebsiteController {

    private final ClientWebsiteService clientWebsiteService;
    private final ClientWebsiteDtoAssembler clientWebsiteDtoAssembler;

    @Autowired
    public ClientWebsiteController(ClientWebsiteService clientWebsiteService, ClientWebsiteDtoAssembler clientWebsiteDtoAssembler) {
        this.clientWebsiteService = clientWebsiteService;
        this.clientWebsiteDtoAssembler = clientWebsiteDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ClientWebsiteDto>> getClientWebsites() {
        return ResponseEntity.ok(clientWebsiteDtoAssembler.toCollectionModel(clientWebsiteService.getClientWebsites()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientWebsiteDto> getClientWebsite(@PathVariable("id") Long id)  {
        return clientWebsiteService.getClientWebsite(id) //
                .map(website -> {
                    ClientWebsiteDto websiteDto = clientWebsiteDtoAssembler.toModel(website)
                            .add(linkTo(methodOn(ClientWebsiteController.class).getClientWebsites()).withRel("client_websites"));

                    return ResponseEntity.ok(websiteDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
