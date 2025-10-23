package co.edu.uniquindio.citycourier.citycourier.model.builder;

import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBuilder {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private tipoUsuario tipo;
    private List<Direccion> direcciones = new ArrayList<>();

    public UsuarioBuilder setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public UsuarioBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder setCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioBuilder setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public UsuarioBuilder setContrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public UsuarioBuilder setTipo(tipoUsuario tipo) {
        this.tipo = tipo;
        return this;
    }

    public UsuarioBuilder agregarDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
        return this;
    }

    public Usuario build() {
        Usuario usuario = new Usuario(idUsuario, nombre, correo, telefono, contrasena, tipo);
        for (Direccion d : direcciones) {
            usuario.agregarDireccion(d);
        }
        return usuario;
    }
}
