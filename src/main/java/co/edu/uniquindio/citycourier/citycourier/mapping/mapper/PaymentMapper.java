package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.Payment;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.PaymentDTO;

public final class PaymentMapper {
    private PaymentMapper() {}

    public static PaymentDTO toDTO(Payment p) {
        if (p == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.idPago = p.getIdPago();
        dto.monto = p.getMonto();
        dto.fecha = p.getFecha();
        dto.metodoPago = p.getMetodoPago();
        dto.estado = p.getEstado();
        return dto;
    }
}
