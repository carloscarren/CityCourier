package co.edu.uniquindio.citycourier.citycourier.model;

public class Direccion {
    private String idDireccion;
    private String barrio;
    private String direccion;
    private String ciudad;
    private String referencia;

    public Direccion() {

    }
    public Direccion(String idDireccion, String barrio, String direccion, String ciudad, String referencia) {
        this.idDireccion = idDireccion;
        this.barrio = barrio;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.referencia = referencia;
    }

    public String getIdDireccion() {
        return idDireccion;
    }
    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }
    public String getBarrio() {
        return barrio;
    }
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    public String getDireccion() {
        return direccion;

    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    @Override
    public String toString() {
        return barrio + " " + direccion + " " + ciudad + " " + referencia;
    }
}

