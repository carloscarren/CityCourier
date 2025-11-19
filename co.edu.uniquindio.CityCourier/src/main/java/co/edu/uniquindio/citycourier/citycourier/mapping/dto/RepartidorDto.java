package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor;

public record RepartidorDto(
        String idRepartidor,
        String nombre,
        String telefono,
        String vehiculoAsignado,
        String zonaCobertura,
        estadoRepartidor estado
) {
    // Constructor sobrecargado para compatibilidad con código existente
    public RepartidorDto(String idRepartidor, String nombre, String telefono,
                        String vehiculoAsignado, String zonaCobertura) {
        this(idRepartidor, nombre, telefono, vehiculoAsignado, zonaCobertura, estadoRepartidor.ACTIVO);
    }
}
