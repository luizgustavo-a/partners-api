package ldelivery.api_partners.infra.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ldelivery.api_partners.domain.entities.partner.Partner;
import ldelivery.api_partners.infra.gateway.AddressMapper;
import ldelivery.api_partners.infra.gateway.CoverageAreaMapper;

@Entity
@Table(name = "partners")
public class PartnerEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String tradingName;
    @NotBlank
    private String ownerName;
    @Column(unique = true)
    @Pattern(
            regexp = "^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})|(\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})$",
            message = "Invalid document format. Should be CPF or CNPJ."
    )
    @NotBlank
    private String document;
    @Embedded
    private CoverageAreaEntity coverageArea;
    @Embedded
    private AddressEntity address;
    private Boolean active;

    public PartnerEntity(Long id, String tradingName, String ownerName, String document,
                         CoverageAreaEntity coverageArea, AddressEntity address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
        this.active = true;
    }

    public PartnerEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDocument() {
        return document;
    }

    public CoverageAreaEntity getCoverageArea() {
        return coverageArea;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public Boolean getActive() {
        return active;
    }

    public void updatePartner(Partner partner) {
        if (partner.getTradingName() != null) {
            this.tradingName = partner.getTradingName();
        }
        if (partner.getOwnerName() != null) {
            this.ownerName = partner.getOwnerName();
        }
        if (partner.getCoverageArea() != null) {
            CoverageAreaMapper coverageAreaMapper = new CoverageAreaMapper();
            this.coverageArea = coverageAreaMapper.toEntity(partner.getCoverageArea());
        }
        if (partner.getAddress() != null) {
            AddressMapper addressMapper = new AddressMapper();
            this.address = addressMapper.toEntity(partner.getAddress());
        }
    }

    public void delete() {
        this.active = false;
    }
}
