package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor;
import java.util.ArrayList;
import java.util.List;
public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String vehiculoAsignado;
    private estadoRepartidor estado;
    private String zonaCobertura;
    private List<String> enviosAsignados;

    public Repartidor() {
        this.estado = estadoRepartidor.INACTIVO;
        this.enviosAsignados = new ArrayList<>();
    }
    public Repartidor(String idRepartidor, String nombre, String documento,
                      String telefono, String vehiculoAsignado, String zonaCobertura,
                      List<String> enviosAsignados) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.vehiculoAsignado = vehiculoAsignado;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = enviosAsignados != null ? enviosAsignados : new ArrayList<>();
        this.estado = estadoRepartidor.ACTIVO;
    }

    // ================= MÉTODOS =================
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

    // ================= GETTERS Y SETTERS =================
    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public void setVehiculoAsignado(String vehiculoAsignado) {
        this.vehiculoAsignado = vehiculoAsignado;
    }

    public estadoRepartidor getEstado() {
        return estado;
    }

    public void setEstado(estadoRepartidor estado) {
        this.estado = estado;
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
        return nombre + " (" + zonaCobertura + ") - " + estado;
    }
}
