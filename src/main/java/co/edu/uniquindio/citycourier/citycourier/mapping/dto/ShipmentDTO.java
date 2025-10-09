package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import co.edu.uniquindio.citycourier.citycourier.domain.Priority;
import co.edu.uniquindio.citycourier.citycourier.domain.ShippingStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class ShipmentDTO {
    public String idEnvio;
    public AddressDTO origen;
    public AddressDTO destino;
    public double pesoKg;
    public double volumenM3;
    public Priority prioridad;
    public ShippingStatus estado;
    public LocalDateTime fechaCreacion;
    public LocalDateTime fechaEstimadaEntrega;
    public String idUsuario;
    public String idRepartidor;
    public Set<String> servicios; // nombres de servicios
    public double costo;
}
