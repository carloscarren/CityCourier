package co.edu.uniquindio.citycourier.citycourier.model;

public class Direccion {
    private String idDireccion;
    private final String calle;
    private final String ciudad;
    private final String complemento;

    public Direccion(String calle, String ciudad, String complemento) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.complemento = complemento;
    }

    public Direccion(String idDireccion, String calle, String ciudad, String complemento) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.ciudad = ciudad;
        this.complemento = complemento;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getComplemento() { return complemento; }

    @Override
    public String toString() {
        return calle + ", " + ciudad + (complemento != null ? " (" + complemento + ")" : "");
    }
}

