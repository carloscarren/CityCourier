package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario dentro del sistema CityCourier.
 * Cada usuario hereda los datos básicos de Persona y puede tener direcciones frecuentes,
 * métodos de pago y un tipo específico (CLIENTE o ADMIN).
 */
public class Usuario extends Persona {

    // ======================== ATRIBUTOS ========================
    private String idUsuario;
    private String telefono;
    private String contrasena;
    private tipoUsuario tipo;

    private List<Direccion> direccionesFrecuentes;
    private List<String> metodosPago;
    private List<Cuenta> listaCuentas;

    // ======================== CONSTRUCTORES ========================

    public Usuario() {
        super();
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
        this.listaCuentas = new ArrayList<>();
        inicializarMetodosPago();
    }

    public Usuario(String idUsuario, String nombre, String apellido, String correo, String telefono,
                   String contrasena, tipoUsuario tipo) {
        super(nombre, apellido, correo);
        this.idUsuario = idUsuario;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
        this.listaCuentas = new ArrayList<>();
        inicializarMetodosPago();
    }

    // ======================== MÉTODOS AUXILIARES ========================

    private void inicializarMetodosPago() {
        metodosPago.add("Efectivo");
        metodosPago.add("Tarjeta de crédito");
        metodosPago.add("PSE");
    }

    public void agregarDireccionFrecuente(Direccion direccion) {
        if (direccion != null && !direccionesFrecuentes.contains(direccion)) {
            direccionesFrecuentes.add(direccion);
        }
    }

    public void eliminarDireccionFrecuente(Direccion direccion) {
        direccionesFrecuentes.remove(direccion);
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
        if (metodoPago != null && !metodosPago.contains(metodoPago)) {
            metodosPago.add(metodoPago);
        }
    }

    public void eliminarMetodoPago(String metodoPago) {
        metodosPago.remove(metodoPago);
    }

    // ======================== GETTERS Y SETTERS ========================

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    // ======================== TO STRING ========================

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + getNombre() + " " + getApellido() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
