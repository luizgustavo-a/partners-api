package ldelivery.api_partners.infra.controller;

import jakarta.validation.Valid;
import ldelivery.api_partners.application.usecases.*;
import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.controller.dto.PartnerDto;
import ldelivery.api_partners.infra.controller.dto.UpdatePartnerDto;
import ldelivery.api_partners.infra.gateway.PartnerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final LoadPartner loadPartner;
    private final SearchPartner searchPartner;
    private final UpdatePartner updatePartner;
    private final DeletePartner deletePartner;
    private static final PartnerMapper partnerMapper = new PartnerMapper();

    public PartnerController(CreatePartner createPartner, LoadPartner loadPartner, SearchPartner searchPartner,
                             UpdatePartner updatePartner, DeletePartner deletePartner) {
        this.createPartner = createPartner;
        this.loadPartner = loadPartner;
        this.searchPartner = searchPartner;
        this.updatePartner = updatePartner;
        this.deletePartner = deletePartner;
    }

    @PostMapping
    public ResponseEntity<PartnerDto> createPartner(@RequestBody @Valid PartnerDto dto) {
        Partner partner = createPartner.createPartner(partnerMapper.toDomain(dto));
        PartnerDto partnerDto = partnerMapper.toDto(partner);
        return ResponseEntity.ok(partnerDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDto> loadPartnerById(@PathVariable Long id) {
        Partner partner = loadPartner.loadPartnerById(id);
        return ResponseEntity.ok(partnerMapper.toDto(partner));
    }

    @GetMapping("search/closest")
    public ResponseEntity<PartnerDto> searchPartner(@RequestParam Double latitude,
                                                    @RequestParam Double longitude) {
        var partner = searchPartner.searchPartner(latitude, longitude);
        return ResponseEntity.ok(partnerMapper.toDto(partner));
    }

    @GetMapping("search")
    public ResponseEntity<Page<PartnerDto>> searchPartnersInAddress(@RequestParam Double latitude,
                                                                    @RequestParam Double longitude,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                    @RequestParam(value = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        var paginatedPartners = searchPartner.searchPartnersInAddress(latitude, longitude, pageable);

        return ResponseEntity.ok(paginatedPartners.map(partnerMapper::toDto));
    }

    @GetMapping
    public ResponseEntity<Page<PartnerDto>> loadPartners(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(loadPartner.loadAllPartners(pageable).map(partnerMapper::toDto));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PartnerDto> updatePartner(@RequestBody @Valid UpdatePartnerDto dto) {
        var partner = partnerMapper.toDomain(dto);
        return ResponseEntity.ok(partnerMapper.toDto(updatePartner.updatePartner(partner)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<PartnerDto> delete(@PathVariable Long id) {
        var partner = deletePartner.deletePartner(id);
        return ResponseEntity.ok(partnerMapper.toDto(partner));
    }

}
