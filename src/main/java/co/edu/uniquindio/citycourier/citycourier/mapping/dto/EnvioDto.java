package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import java.time.LocalDate;

public record EnvioDto(
        String idEnvio,
        String direccionOrigen,
        String direccionDestino,
        String descripcion,
        double peso,
        double volumen,
        double costo,
        String fechaEstimadaEntrega,
        String idUsuario
) {}