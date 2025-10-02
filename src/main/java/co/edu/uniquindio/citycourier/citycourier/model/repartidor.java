package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor;

import java.util.ArrayList;
import java.util.List;

public class repartidor {
    private String idRepartidor;
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private estadoRepartidor estado;
    private String zonaCobertura;
    private List<String> enviosAsignados;

    public repartidor() {
        this.estado = estadoRepartidor.INACTIVO;
        this.enviosAsignados = new ArrayList<>();

    }

    public repartidor(String idRepartidor, String nombreCompleto, String documento, String telefono, String zonaCobertura, List<String> enviosAsignados) {
        this.idRepartidor = idRepartidor;
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = enviosAsignados;
        this.estado = estadoRepartidor.ACTIVO;
    }

    public void asignarEnvio(String idEnvio) {
        if (!enviosAsignados.contains(idEnvio)) {
            enviosAsignados.add(idEnvio);

            if (estado == estadoRepartidor.ACTIVO) {
                estado = estadoRepartidor.EN_RUTA;
            }
        }
    }

    public boolean estadoDisponible() {
        return estado == estadoRepartidor.ACTIVO;
    }

    public int cantidadEnviosAsignados() {
        return enviosAsignados.size();
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void sutIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public List<String> getEnviosAsignados() {
        return enviosAsignados;
    }

    public void setEnviosAsignados(List<String> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }

    @Override
    public String toString() {
        return nombreCompleto + " " + zonaCobertura + " (" + estado + ")";
    }
}
