package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoPago;

import java.time.LocalDate;

public class Pago {
    private String idPago;
    private String idEnvio;
    private double monto;
    private LocalDate fecha;
    private String metodoPago;
    private estadoPago resultado;
    private String numeroTransaccion;
    private String observaciones;

    public Pago() {
        this.fecha = LocalDate.now();
        this.resultado = estadoPago.RECHAZADO;
    }

    public Pago(String idPago, String idEnvio, double monto, String metodoPago, String numeroTransaccion) {
        this.idPago = idPago;
        this.idEnvio = idEnvio;
        this.monto = monto;
        this.fecha = LocalDate.now();
        this.metodoPago = metodoPago;
        this.resultado = estadoPago.RECHAZADO;
        this.numeroTransaccion = numeroTransaccion;

    }

    public void procesarPago() {
        if (validarMetodoPago() && validarMonto()) {
            this.resultado = estadoPago.APROBADO;
            this.observaciones = "Pago aprobado exitosamente.";
        } else {
            this.resultado = estadoPago.RECHAZADO;
            this.observaciones = "Pago rechazado. Verifique los datos ingresados.";
        }
    }

    private boolean validarMetodoPago() {
        return metodoPago != null && !metodoPago.isEmpty() &&
                (metodoPago.equals("Efectivo") || metodoPago.equals("Tarjeta de credito") || metodoPago.equals("PSE"));
    }

    private boolean validarMonto() {
        return monto > 0;
    }

    private String generarNumeroTransaccion() {
        return "TXN" + System.currentTimeMillis();
    }

    public String generarComprobante() {
        StringBuilder comprobante = new StringBuilder();
        comprobante.append("----- Comprobante de Pago -----\n");
        comprobante.append("ID Pago: ").append(idPago).append("\n");
        comprobante.append("ID Envío: ").append(idEnvio).append("\n");
        comprobante.append("Monto: $").append(String.format("%.2f", monto)).append("\n");
        comprobante.append("Fecha: ").append(fecha).append("\n");
        comprobante.append("Método de Pago: ").append(metodoPago).append("\n");
        comprobante.append("Número de Transacción: ").append(numeroTransaccion).append("\n");
        comprobante.append("Estado: ").append(resultado).append("\n");
        if (observaciones != null) {
            comprobante.append("Observaciones: ").append(observaciones).append("\n");
        }
        comprobante.append("-------------------------------\n");
        return comprobante.toString();
    }

    public boolean esAprobado() {
        return resultado == estadoPago.APROBADO;
    }

    public boolean esRechazado() {
        return resultado == estadoPago.RECHAZADO;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public estadoPago getResultado() {
        return resultado;
    }

    public void setResultado(estadoPago resultado) {
        this.resultado = resultado;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
    return"Pago{"+
            "id='"+idPago +'\''+
            ", monto="+monto +
            ", metodo='"+metodoPago +'\''+
            ", resultado="+resultado +
            ", fecha="+fecha +
            '}';
    }
}

