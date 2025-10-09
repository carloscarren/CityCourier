package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.Address;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.AddressDTO;

public final class AddressMapper {
    private AddressMapper() {}

    public static AddressDTO toDTO(Address a) {
        if (a == null) return null;
        AddressDTO dto = new AddressDTO();
        dto.idDireccion = a.getIdDireccion();
        dto.alias = a.getAlias();
        dto.calle = a.getCalle();
        dto.ciudad = a.getCiudad();
        dto.latitud = a.getLatitud();
        dto.longitud = a.getLongitud();
        return dto;
    }
}
