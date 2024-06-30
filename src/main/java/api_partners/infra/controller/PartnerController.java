package api_partners.infra.controller;

import api_partners.application.usecases.CreatePartner;
import api_partners.application.usecases.LoadPartner;
import api_partners.application.usecases.SearchPartner;
import api_partners.domain.entities.partner.Partner;
import api_partners.infra.gateway.PartnerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final LoadPartner loadPartner;
    private final SearchPartner searchPartner;
    private static final PartnerMapper partnerMapper = new PartnerMapper();

    public PartnerController(CreatePartner createPartner, LoadPartner loadPartner, SearchPartner searchPartner) {
        this.createPartner = createPartner;
        this.loadPartner = loadPartner;
        this.searchPartner = searchPartner;
    }

    @PostMapping
    public ResponseEntity<PartnerDto> createPartner (@RequestBody PartnerDto dto) {
        try{
            Partner partner = createPartner.createPartner(partnerMapper.toDomain(dto));
            PartnerDto partnerDto = partnerMapper.toDto(partner);
            return ResponseEntity.ok(partnerDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/populate-database")
    public ResponseEntity<List<PartnerDto>> createPartner (@RequestBody MultiplePartnersDto dto) {
        List<PartnerDto> partnerDtoList = new ArrayList<>();
        for (PartnerDto partner : dto.pdvs()) {
            createPartner(partner);
        }
        return ResponseEntity.ok(partnerDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDto> loadPartnerById (@PathVariable Long id) {
        try {
            Partner partner= loadPartner.loadPartnerById(id);
            return ResponseEntity.ok(partnerMapper.toDto(partner));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("search/lat={latitude}&long={longitude}")
    public ResponseEntity<PartnerDto> searchPartner (@PathVariable Double latitude,
                                                     @PathVariable Double longitude) {
        try {
            var partner = searchPartner.searchPartner(latitude, longitude);
            return ResponseEntity.ok(partnerMapper.toDto(partner));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PartnerDto>> loadPartners() {
        return ResponseEntity.ok(loadPartner.loadAllPartners().stream()
                .map(partnerMapper::toDto)
                .collect(Collectors.toList()));
    }

}
