package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

import java.time.LocalDateTime;

public class envio {
    private String idEnvio;
    private direccion origen;
    private direccion destino;
    private String descripcionPaquete;
    private double peso;
    private double volumen;
    private double costo;
    private estadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private String idUsuario;

}
