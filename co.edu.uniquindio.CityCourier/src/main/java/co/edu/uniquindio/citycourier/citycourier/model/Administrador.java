package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import java.util.ArrayList;
import java.util.List;

/**
 * ✅ Clase que representa al administrador del sistema CityCourier.
 * Se encarga de gestionar usuarios, repartidores, envíos y métricas generales.
 */
public class Administrador extends Persona {

    private String idAdministrador;
    private String contrasena;


    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Repartidor> listaRepartidores = new ArrayList<>();
    private List<Envio> listaEnvios = new ArrayList<>();


    public Administrador(String nombre, String apellido, String correo, String idAdministrador, String contrasena) {
        super(nombre, apellido, correo);
        this.idAdministrador = idAdministrador;
        this.contrasena = contrasena;
    }

    public Administrador() {
        super();
    }
    
    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            return false;
        }
        if (obtenerUsuario(usuario.getIdUsuario()) == null) {
            listaUsuarios.add(usuario);
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuario(String idUsuario) {
        if (idUsuario == null) {
            return null;
        }
        for (Usuario u : listaUsuarios) {
            if (u != null && u.getIdUsuario() != null && u.getIdUsuario().equalsIgnoreCase(idUsuario)) {
                return u;
            }
        }
        return null;
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            listaUsuarios.remove(usuario);
            return true;
        }
        return false;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
    public boolean crearRepartidor(Repartidor repartidor) {
        if (repartidor == null || repartidor.getIdRepartidor() == null) {
            return false;
        }
        if (obtenerRepartidor(repartidor.getIdRepartidor()) == null) {
            listaRepartidores.add(repartidor);
            return true;
        }
        return false;
    }

    public Repartidor obtenerRepartidor(String idRepartidor) {
        if (idRepartidor == null) {
            return null;
        }
        for (Repartidor r : listaRepartidores) {
            if (r != null && r.getIdRepartidor() != null && r.getIdRepartidor().equalsIgnoreCase(idRepartidor)) {
                return r;
            }
        }
        return null;
    }

    public List<Repartidor> listarRepartidores() {
        return new ArrayList<>(listaRepartidores);
    }

    // ===================== MÉTODOS DE ENVÍOS =====================

    public boolean registrarEnvio(Envio envio) {
        if (envio == null || envio.getIdEnvio() == null) {
            return false;
        }
        if (obtenerEnvio(envio.getIdEnvio()) == null) {
            listaEnvios.add(envio);
            return true;
        }
        return false;
    }

    public Envio obtenerEnvio(String idEnvio) {
        if (idEnvio == null) {
            return null;
        }
        for (Envio e : listaEnvios) {
            if (e != null && e.getIdEnvio() != null && e.getIdEnvio().equalsIgnoreCase(idEnvio)) {
                return e;
            }
        }
        return null;
    }

    public boolean eliminarEnvio(String idEnvio) {
        Envio envio = obtenerEnvio(idEnvio);
        if (envio != null) {
            listaEnvios.remove(envio);
            return true;
        }
        return false;
    }

    public boolean actualizarEstadoEnvio(String idEnvio, estadoEnvio nuevoEstado) {
        Envio envio = obtenerEnvio(idEnvio);
        if (envio != null) {
            envio.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public boolean asignarRepartidorEnvio(String idEnvio, String idRepartidor) {
        Envio envio = obtenerEnvio(idEnvio);
        Repartidor repartidor = obtenerRepartidor(idRepartidor);
        if (envio != null && repartidor != null) {
            envio.setRepartidorAsignado(repartidor.getIdRepartidor());
            envio.setEstado(estadoEnvio.EN_RUTA);
            return true;
        }
        return false;
    }

    public List<Envio> listarEnvios() {
        return new ArrayList<>(listaEnvios);
    }

    // ===================== MÉTRICAS =====================

    public long contarEnviosPorEstado(estadoEnvio estado) {
        return listaEnvios.stream().filter(e -> e.getEstado() == estado).count();
    }

    public double calcularTotalIngresos() {
        return listaEnvios.stream().mapToDouble(Envio::getCosto).sum();
    }

    public double promedioCostoEnvio() {
        if (listaEnvios.isEmpty()) return 0;
        return calcularTotalIngresos() / listaEnvios.size();
    }

    // ===================== GETTERS & SETTERS =====================

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    public void setListaRepartidores(List<Repartidor> listaRepartidores) {
        this.listaRepartidores = listaRepartidores;
    }

    public List<Envio> getListaEnvios() {
        return listaEnvios;
    }

    public void setListaEnvios(List<Envio> listaEnvios) {
        this.listaEnvios = listaEnvios;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "idAdministrador='" + idAdministrador + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", usuarios=" + listaUsuarios.size() +
                ", repartidores=" + listaRepartidores.size() +
                ", envíos=" + listaEnvios.size() +
                '}';
    }
}
