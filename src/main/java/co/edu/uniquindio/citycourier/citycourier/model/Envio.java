package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

import java.time.LocalDateTime;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private String descripcionPaquete;
    private double peso;
    private double volumen;
    private double costo;
    private estadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private String idUsuario;
    private String idRepartidor;

    public Envio() {
        this.estado = estadoEnvio.SOLICITANDO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Envio(String idEnvio, Direccion origen, Direccion destino, String descripcionPaquete, double peso, double volumen, double costo, LocalDateTime fechaEstimadaEntrega, String idUsuario) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.descripcionPaquete = descripcionPaquete;
        this.peso = peso;
        this.volumen = volumen;
        this.costo = costo;
        this.estado = estadoEnvio.SOLICITANDO;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public void cambiarEstado(estadoEnvio nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public boolean puedeCancelar() {
        return estado == estadoEnvio.SOLICITANDO;
    }

    public void asignarRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
        this.estado = estadoEnvio.EN_RUTA;
    }

    public void marcarEnRuta() {
        this.estado = estadoEnvio.EN_RUTA;
    }

    public void marcarEntregado() {
        this.estado = estadoEnvio.ENTREGADO;
    }

    public void marcarIncidencia() {
        this.estado = estadoEnvio.INCIDENCIA;
    }
    public String getIdEnvio() {
        return idEnvio;
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
    public estadoEnvio getEstado() {
        return estado;
    }
    public void setEstado(estadoEnvio estado) {
        this.estado = estado;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getIdRepartidor() {
        return idRepartidor;
    }
    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
    @Override
    public String toString() {
        return "envio{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", descripcionPaquete='" + descripcionPaquete + '\'' +
                ", peso=" + peso +
                ", volumen=" + volumen +
                ", costo=" + costo +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEstimadaEntrega=" + fechaEstimadaEntrega +
                ", idUsuario='" + idUsuario + '\'' +
                ", idRepartidor='" + idRepartidor + '\'' +
                '}';
    }
}




