package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.metodoPago;

import java.time.LocalDateTime;

/**
 * Representa un envío dentro del sistema CityCourier.
 * Contiene información sobre el paquete, su origen, destino, costo, estado y usuario asociado.
 */
public class Envio {

    // ======================== ATRIBUTOS ========================
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private String descripcionPaquete;
    private double peso;
    private double volumen;
    private double costo;
    private metodoPago metodoPago;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private estadoEnvio estado;
    private String idUsuarioAsociado;
    private String repartidorAsignado;

    // ======================== CONSTRUCTORES ========================

    public Envio() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = estadoEnvio.SOLICITANDO;
        this.metodoPago = metodoPago.EFECTIVO;
    }

    public Envio(String idEnvio, Direccion origen, Direccion destino, String descripcionPaquete,
                 double peso, double volumen, double costo,
                 metodoPago metodoPago, LocalDateTime fechaEstimadaEntrega, String idUsuarioAsociado) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.descripcionPaquete = descripcionPaquete;
        this.peso = peso;
        this.volumen = volumen;
        this.costo = costo;
        this.metodoPago = metodoPago;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.idUsuarioAsociado = idUsuarioAsociado;
        this.estado = estadoEnvio.SOLICITANDO;
    }

    // ======================== MÉTODOS ========================

    public void asignarRepartidor(String repartidor) {
        if (this.estado == estadoEnvio.SOLICITANDO) {
            this.repartidorAsignado = repartidor;
            this.estado = estadoEnvio.EN_RUTA;
        }
    }

    public void entregar() {
        if (this.estado == estadoEnvio.EN_RUTA) {
            this.estado = estadoEnvio.ENTREGADO;
        }
    }

    public void cancelar() {
        if (this.estado == estadoEnvio.SOLICITANDO) {
            this.estado = estadoEnvio.CANCELADO;
        }
    }

    // ======================== GETTERS & SETTERS ========================

    public String getIdEnvio() {
        return idEnvio;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Direccion getOrigen() {
        return origen;
    }

    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public void setDestino(Direccion destino) {
        this.destino = destino;
    }

    public String getDescripcionPaquete() {
        return descripcionPaquete;
    }

    public void setDescripcionPaquete(String descripcionPaquete) {
        this.descripcionPaquete = descripcionPaquete;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public metodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(metodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public estadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(estadoEnvio estado) {
        this.estado = estado;
    }

    public String getIdUsuarioAsociado() {
        return idUsuarioAsociado;
    }

    public void setIdUsuarioAsociado(String idUsuarioAsociado) {
        this.idUsuarioAsociado = idUsuarioAsociado;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    // ======================== TO STRING ========================

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", descripcionPaquete='" + descripcionPaquete + '\'' +
                ", peso=" + peso +
                ", volumen=" + volumen +
                ", costo=" + costo +
                ", estado=" + estado +
                ", idUsuarioAsociado='" + idUsuarioAsociado + '\'' +
                '}';
    }


}
