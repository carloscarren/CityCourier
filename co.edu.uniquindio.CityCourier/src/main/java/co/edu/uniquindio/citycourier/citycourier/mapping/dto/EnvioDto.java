package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

public record EnvioDto(
        String idEnvio,
        String direccionOrigen,
        String direccionDestino,
        String descripcion,
        double peso,
        double volumen,
        double costo,
        String fechaEntrega,
        String idUsuario,
        estadoEnvio estado
) {

    public estadoEnvio getEstado() {
        return estado;
    }
}