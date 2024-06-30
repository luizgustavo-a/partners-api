package api_partners.infra.persistence;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Point;

@Embeddable
public class AddressEntity {

    @NotNull
    private Point address;

    public AddressEntity(Point address) {
        this.address = address;
    }

    public AddressEntity(){}

    public Point getAdress() {
        return address;
    }
}

