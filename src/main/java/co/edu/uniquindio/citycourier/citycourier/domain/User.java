package co.edu.uniquindio.citycourier.citycourier.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private List<Address> direccionesFrecuentes;
    private List<PaymentMethod> metodosPago;

    public User() {
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
    }

    public User(String idUsuario, String nombreCompleto, String correo, String telefono) {
        this();
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Address> getDireccionesFrecuentes() {
        return direccionesFrecuentes;
    }

    public List<PaymentMethod> getMetodosPago() {
        return metodosPago;
    }
}
