package api_partners.infra.gateway;

import api_partners.infra.controller.AddressDto;
import api_partners.domain.geolocation.Address;
import api_partners.infra.persistence.AddressEntity;

public class AddressMapper {

    AddressDto toDto(Address address) {
        return new AddressDto(address.getType(),
                GeoJsonConverter.toCoordinates(address.getAdress()));
    }

    Address toDomain (AddressDto addressDto) {
        return new Address(addressDto.type(),
                GeoJsonConverter.toPoint(addressDto.coordinates()));
    }

    Address toDomain (AddressEntity addressEntity) {
        return new Address("Point", addressEntity.getAdress());
    }

    AddressEntity toEntity (Address address) {
        return new AddressEntity(address.getAdress());
    }

}
