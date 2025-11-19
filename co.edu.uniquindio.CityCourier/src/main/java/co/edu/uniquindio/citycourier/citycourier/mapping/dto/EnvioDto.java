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
        estadoEnvio estado,
        String idRepartidor
) {

    public estadoEnvio getEstado() {
        return estado;
    }
    
    // Constructor sobrecargado para compatibilidad con código existente
    public EnvioDto(String idEnvio, String direccionOrigen, String direccionDestino,
                   String descripcion, double peso, double volumen, double costo,
                   String fechaEntrega, String idUsuario, estadoEnvio estado) {
        this(idEnvio, direccionOrigen, direccionDestino, descripcion, peso, volumen, 
             costo, fechaEntrega, idUsuario, estado, null);
    }
}