package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

public record PagoDto(
        String idPago,
        String idEnvio,
        double monto,
        String metodoPago,
        String resultado,
        String fecha
) {}