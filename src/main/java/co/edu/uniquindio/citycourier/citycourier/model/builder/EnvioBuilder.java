package co.edu.uniquindio.citycourier.citycourier.model.builder;

import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.metodoPago;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;

import java.time.LocalDateTime;

public class EnvioBuilder {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private String descripcionPaquete;
    private double peso;
    private double volumen;
    private double costo;
    private metodoPago metodoPago;
    private LocalDateTime fechaEstimadaEntrega;
    private String idUsuario;

    public EnvioBuilder setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public EnvioBuilder setOrigen(Direccion origen) {
        this.origen = origen;
        return this;
    }

    public EnvioBuilder setDestino(Direccion destino) {
        this.destino = destino;
        return this;
    }

    public EnvioBuilder setDescripcionPaquete(String descripcionPaquete) {
        this.descripcionPaquete = descripcionPaquete;
        return this;
    }

    public EnvioBuilder setPeso(double peso) {
        this.peso = peso;
        return this;
    }

    public EnvioBuilder setVolumen(double volumen) {
        this.volumen = volumen;
        return this;
    }

    public EnvioBuilder setCosto(double costo) {
        this.costo = costo;
        return this;
    }

    public EnvioBuilder setMetodoPago(metodoPago metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public EnvioBuilder setFechaEstimadaEntrega(LocalDateTime fecha) {
        this.fechaEstimadaEntrega = fecha;
        return this;
    }

    public EnvioBuilder setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public Envio build() {
        return new Envio(idEnvio, origen, destino, descripcionPaquete, peso, volumen, costo,
                metodoPago, fechaEstimadaEntrega, idUsuario);
    }
}
