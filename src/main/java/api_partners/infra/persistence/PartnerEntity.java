package api_partners.infra.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "partners")
public class PartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public PartnerEntity(Long id, String tradingName, String ownerName, String document,
                         CoverageAreaEntity coverageArea, AddressEntity address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
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
}
