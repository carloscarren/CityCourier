package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.Courier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.CourierDTO;

public final class CourierMapper {
    private CourierMapper() {}

    public static CourierDTO toDTO(Courier c) {
        if (c == null) return null;
        CourierDTO dto = new CourierDTO();
        dto.idRepartidor = c.getIdRepartidor();
        dto.nombre = c.getNombre();
        dto.documento = c.getDocumento();
        dto.telefono = c.getTelefono();
        dto.zonaCobertura = c.getZonaCobertura();
        dto.disponibilidad = c.getDisponibilidad();
        return dto;
    }
}
