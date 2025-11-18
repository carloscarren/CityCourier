package co.edu.uniquindio.citycourier.citycourier.model.builder;

import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBuilder {


    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private tipoUsuario tipo = tipoUsuario.CLIENTE; // Por defecto es CLIENTE
    private List<Direccion> direccionesFrecuentes = new ArrayList<>();
    private List<String> metodosPago = new ArrayList<>();

    // ======================== MÉTODOS BUILDER ========================

    public UsuarioBuilder idUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public UsuarioBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder correo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public UsuarioBuilder contrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public UsuarioBuilder tipo(tipoUsuario tipo) {
        this.tipo = tipo;
        return this;
    }

    public UsuarioBuilder direccionesFrecuentes(List<Direccion> direcciones) {
        if (direcciones != null) {
            this.direccionesFrecuentes = direcciones;
        }
        return this;
    }

    public UsuarioBuilder metodosPago(List<String> metodosPago) {
        if (metodosPago != null) {
            this.metodosPago = metodosPago;
        }
        return this;
    }


    public Usuario build() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(this.idUsuario);
        usuario.setNombre(this.nombre);
        usuario.setCorreo(this.correo);
        usuario.setTelefono(this.telefono);
        usuario.setContrasena(this.contrasena);
        usuario.setTipo(this.tipo);
        usuario.setDireccionesFrecuentes(this.direccionesFrecuentes);
        usuario.setMetodosPago(this.metodosPago);
        return usuario;
    }
}
