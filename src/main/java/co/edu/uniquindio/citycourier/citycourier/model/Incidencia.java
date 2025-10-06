package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoIncidencia;

import java.time.LocalDateTime;

public class Incidencia {
    private String idIncidencia;
    private String idEnvio;
    private tipoIncidencia tipo;
    private String descripcion;
    private LocalDateTime fecha;

    private Incidencia () {
        this.fecha = LocalDateTime.now();
    }
    public Incidencia(String idIncidencia, String idEnvio, tipoIncidencia tipo, String descripcion) {
        this.idIncidencia = idIncidencia;
        this.idEnvio = idEnvio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }
    public String getIdIncidencia() {return idIncidencia;}
    public void setIdIncidencia(String idIncidencia) {this.idIncidencia = idIncidencia;}
    public String getIdEnvio() {return idEnvio;}
    public void setIdEnvio(String idEnvio) {this.idEnvio = idEnvio;}
    public tipoIncidencia getTipo() {return tipo;}
    public void setTipo(tipoIncidencia tipo) {this.tipo = tipo;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}

}
