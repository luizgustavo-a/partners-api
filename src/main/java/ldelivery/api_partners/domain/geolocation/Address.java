package ldelivery.api_partners.domain.geolocation;

import org.locationtech.jts.geom.Point;

public class Address {
    private String type;
    private Point address;

    public Address(String type, Point address) {
        this.type = type;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public Point getAdress() {
        return address;
    }
}

