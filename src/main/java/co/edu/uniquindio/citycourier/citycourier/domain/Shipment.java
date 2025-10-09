package co.edu.uniquindio.citycourier.citycourier.domain;

import java.time.LocalDateTime;
import java.util.EnumSet;

public class Shipment {
    private String idEnvio;
    private Address origen;
    private Address destino;
    private double pesoKg;
    private double volumenM3;
    private Priority prioridad;
    private ShippingStatus estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private String idUsuario;
    private String idRepartidor; // puede ser null si no asignado
    private EnumSet<AdditionalService> servicios;
    private double costo;

    public Shipment() {
        this.servicios = EnumSet.noneOf(AdditionalService.class);
    }

    public Shipment(String idEnvio, Address origen, Address destino, double pesoKg, double volumenM3, Priority prioridad) {
        this();
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.pesoKg = pesoKg;
        this.volumenM3 = volumenM3;
        this.prioridad = prioridad;
        this.estado = ShippingStatus.SOLICITADO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getIdEnvio() { return idEnvio; }
    public Address getOrigen() { return origen; }
    public Address getDestino() { return destino; }
    public double getPesoKg() { return pesoKg; }
    public double getVolumenM3() { return volumenM3; }
    public Priority getPrioridad() { return prioridad; }
    public ShippingStatus getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public String getIdUsuario() { return idUsuario; }
    public String getIdRepartidor() { return idRepartidor; }
    public EnumSet<AdditionalService> getServicios() { return servicios; }
    public double getCosto() { return costo; }

    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public void setOrigen(Address origen) { this.origen = origen; }
    public void setDestino(Address destino) { this.destino = destino; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public void setVolumenM3(double volumenM3) { this.volumenM3 = volumenM3; }
    public void setPrioridad(Priority prioridad) { this.prioridad = prioridad; }
    public void setEstado(ShippingStatus estado) { this.estado = estado; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public void setServicios(EnumSet<AdditionalService> servicios) { this.servicios = servicios; }
    public void setCosto(double costo) { this.costo = costo; }
}
