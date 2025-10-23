package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private tipoUsuario tipo; // Nuevo campo
    private List<Direccion> direccionesFrecuentes = new ArrayList<>();
    private List<String> metodosPago;

    public Usuario() {
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
        inicializarMetodosPago();
    }

    public Usuario(String idUsuario, String nombre, String correo, String telefono, String contrasena, tipoUsuario tipo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.tipo = tipo;
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
        for (Direccion direccion : direccionesFrecuentes) {
            if (direccion.getIdDireccion().equals(idDireccion)) {
                return direccion;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public tipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(tipoUsuario tipo) {
        this.tipo = tipo;
    }

    public List<Direccion> getDireccionesFrecuentes() {
        return direccionesFrecuentes;
    }

    public void setDireccionesFrecuentes(List<Direccion> direccionesFrecuentes) {
        this.direccionesFrecuentes = direccionesFrecuentes;
    }

    public List<String> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(List<String> metodosPago) {
        this.metodosPago = metodosPago;
    }


    public void agregarDireccion(Direccion direccion) {
        if (direccion != null) {
            direccionesFrecuentes.add(direccion);
        }

    }
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
