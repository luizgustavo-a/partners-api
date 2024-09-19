package ldelivery.api_partners.domain.entities.partner;

import ldelivery.api_partners.domain.geolocation.Address;
import ldelivery.api_partners.domain.geolocation.CoverageArea;

public class Partner {
    private Long id;
    private String tradingName;
    private String ownerName;
    private String document;
    private CoverageArea coverageArea;
    private Address address;

    public Partner() {
    }

    public Partner(Long id, String tradingName, String ownerName, String document, CoverageArea coverageArea, Address address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public CoverageArea getCoverageArea() {
        return coverageArea;
    }

}
