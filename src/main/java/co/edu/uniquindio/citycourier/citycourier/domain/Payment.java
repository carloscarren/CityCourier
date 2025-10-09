package co.edu.uniquindio.citycourier.citycourier.domain;

import java.time.LocalDateTime;

public class Payment {
    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private PaymentMethod metodoPago;
    private PaymentStatus estado;

    public Payment() {
    }

    public Payment(String idPago, double monto, LocalDateTime fecha, PaymentMethod metodoPago, PaymentStatus estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public PaymentMethod getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(PaymentMethod metodoPago) {
        this.metodoPago = metodoPago;
    }

    public PaymentStatus getEstado() {
        return estado;
    }

    public void setEstado(PaymentStatus estado) {
        this.estado = estado;
    }
}
