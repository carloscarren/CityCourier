package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.Shipment;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.ShipmentDTO;

import java.util.stream.Collectors;

public final class ShipmentMapper {
    private ShipmentMapper() {}

    public static ShipmentDTO toDTO(Shipment s) {
        if (s == null) return null;
        ShipmentDTO dto = new ShipmentDTO();
        dto.idEnvio = s.getIdEnvio();
        dto.origen = AddressMapper.toDTO(s.getOrigen());
        dto.destino = AddressMapper.toDTO(s.getDestino());
        dto.pesoKg = s.getPesoKg();
        dto.volumenM3 = s.getVolumenM3();
        dto.prioridad = s.getPrioridad();
        dto.estado = s.getEstado();
        dto.fechaCreacion = s.getFechaCreacion();
        dto.fechaEstimadaEntrega = s.getFechaEstimadaEntrega();
        dto.idUsuario = s.getIdUsuario();
        dto.idRepartidor = s.getIdRepartidor();
        dto.servicios = s.getServicios().stream().map(Enum::name).collect(Collectors.toSet());
        dto.costo = s.getCosto();
        return dto;
    }
}
