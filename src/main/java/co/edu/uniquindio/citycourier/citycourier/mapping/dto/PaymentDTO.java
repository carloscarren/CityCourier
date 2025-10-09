package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import co.edu.uniquindio.citycourier.citycourier.domain.PaymentMethod;
import co.edu.uniquindio.citycourier.citycourier.domain.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentDTO {
    public String idPago;
    public double monto;
    public LocalDateTime fecha;
    public PaymentMethod metodoPago;
    public PaymentStatus estado;
}
