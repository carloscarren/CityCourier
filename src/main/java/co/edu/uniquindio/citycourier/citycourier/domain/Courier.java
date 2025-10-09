package co.edu.uniquindio.citycourier.citycourier.domain;

public class Courier {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String zonaCobertura;
    private CourierAvailability disponibilidad;

    public Courier() {
    }

    public Courier(String idRepartidor, String nombre, String documento, String telefono, String zonaCobertura, CourierAvailability disponibilidad) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
        this.disponibilidad = disponibilidad;
    }

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

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public CourierAvailability getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(CourierAvailability disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
