package ldelivery.api_partners.infra.controller;

import jakarta.validation.Valid;
import ldelivery.api_partners.application.usecases.*;
import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.gateway.PartnerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<PartnerDto> createPartner (@RequestBody @Valid PartnerDto dto) {
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
        } catch (NoSuchElementException e){
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
    public ResponseEntity<Page<PartnerDto>> loadPartners(Pageable pageable) {
        return ResponseEntity.ok(loadPartner.loadAllPartners(pageable).map(partnerMapper::toDto));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PartnerDto> updatePartner(@RequestBody @Valid UpdatePartnerDto dto) {
        var partner = partnerMapper.toDomain(dto);
        updatePartner.updatePartner(partner);
        return ResponseEntity.ok(partnerMapper.toDto(partner));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        deletePartner.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

}
