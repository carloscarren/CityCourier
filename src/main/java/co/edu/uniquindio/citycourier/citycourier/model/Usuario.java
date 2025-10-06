package co.edu.uniquindio.citycourier.citycourier.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String numeroTelefono;
    private String contrasena;
    private List<Direccion> direccionesFrecuentes;
    private List<String> metodosPago;

    public Usuario() {
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
    }

    public Usuario(String idUsuario, String nombreCompleto, String correoElectronico, String numeroTelefono, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.contrasena = contrasena;
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();

        inicializarMetodosPago();
    }

    private void inicializarMetodosPago() {
        metodosPago.add("Efectivo");
        metodosPago.add("Tarjeta de credito");
        metodosPago.add("PSE");
    }

    public void agregarDireccionFrecuente(Direccion direccionFrecuente) {
        if (!direccionesFrecuentes.contains(direccionFrecuente)) {
            direccionesFrecuentes.add(direccionFrecuente);
        }
    }

    public void eliminarDireccionFrecuente(Direccion direccionFrecuente) {
        direccionesFrecuentes.remove(direccionFrecuente);
    }

    public Direccion buscarDireccionPorId(String idDireccion) {
        for (Direccion direccionFrecuente : direccionesFrecuentes) {
            if (direccionFrecuente.getIdDireccion().equals(idDireccion)) {
                return direccionFrecuente;
            }
        }
        return null;
    }

    public void agregarMetodoPago(String metodoPago) {
        if (!metodosPago.contains(metodoPago)) {
            metodosPago.add(metodoPago);
        }
    }

    public void eliminarMetodoPago(String metodoPago) {
        metodosPago.remove(metodoPago);
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Direccion> getDireccionesFrecuentes() {
        return direccionesFrecuentes;
    }

    public void setDireccionesFrecuentes(List<Direccion> direccionFrecuentes) {
        this.direccionesFrecuentes = direccionFrecuentes;
    }

    public List<String> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(List<String> metodosPago) {
        this.metodosPago = metodosPago;
    }

    @Override
    public String toString() {
        return "usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
