package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del dominio.
 * Representa la plataforma central que gestiona usuarios, repartidores, envíos y pagos.
 * Esta clase es utilizada por la fachada (ModelCityCourier).
 */
public class CityCourier {

    private List<Usuario> listaUsuarios;
    private List<Repartidor> listaRepartidores;
    private List<Envio> listaEnvios;
    private List<Pago> listaPagos;

    public CityCourier() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
        this.listaEnvios = new ArrayList<>();
        this.listaPagos = new ArrayList<>();
    }

    // ============================================================
    // ===================== USUARIOS ==============================
    // ============================================================

    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null || obtenerUsuario(usuario.getIdUsuario()) != null) return false;
        listaUsuarios.add(usuario);
        return true;
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            listaUsuarios.remove(usuario);
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuario(String idUsuario) {
        for (Usuario u : listaUsuarios) {
            if (u.getIdUsuario().equals(idUsuario)) return u;
        }
        return null;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    // ============================================================
    // ===================== REPARTIDORES =========================
    // ============================================================

    public boolean crearRepartidor(Repartidor repartidor) {
        if (repartidor == null || obtenerRepartidor(repartidor.getIdRepartidor()) != null) return false;
        listaRepartidores.add(repartidor);
        return true;
    }

    public boolean eliminarRepartidor(String idRepartidor) {
        Repartidor repartidor = obtenerRepartidor(idRepartidor);
        if (repartidor != null) {
            listaRepartidores.remove(repartidor);
            return true;
        }
        return false;
    }

    public Repartidor obtenerRepartidor(String idRepartidor) {
        for (Repartidor r : listaRepartidores) {
            if (r.getIdRepartidor().equals(idRepartidor)) return r;
        }
        return null;
    }

    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    // ============================================================
    // ===================== ENVÍOS ===============================
    // ============================================================

    public boolean crearEnvio(Envio envio) {
        if (envio == null || obtenerEnvio(envio.getIdEnvio()) != null) return false;
        envio.setEstado(estadoEnvio.SOLICITANDO);
        listaEnvios.add(envio);
        return true;
    }

    public Envio obtenerEnvio(String idEnvio) {
        for (Envio e : listaEnvios) {
            if (e.getIdEnvio().equals(idEnvio)) return e;
        }
        return null;
    }

    public boolean actualizarEnvio(Envio envioActualizado) {
        Envio envio = obtenerEnvio(envioActualizado.getIdEnvio());
        if (envio != null) {
            envio.setDescripcionPaquete(envioActualizado.getDescripcionPaquete());
            envio.setDestino(envioActualizado.getDestino());
            envio.setOrigen(envioActualizado.getOrigen());
            envio.setCosto(envioActualizado.getCosto());
            envio.setPeso(envioActualizado.getPeso());
            envio.setVolumen(envioActualizado.getVolumen());
            envio.setEstado(envioActualizado.getEstado());
            return true;
        }
        return false;
    }

    public boolean eliminarEnvio(String idEnvio) {
        Envio envio = obtenerEnvio(idEnvio);
        if (envio != null && envio.getEstado() == estadoEnvio.SOLICITANDO) {
            listaEnvios.remove(envio);
            return true;
        }
        return false;
    }

    public List<Envio> getListaEnvios() {
        return listaEnvios;
    }

    // ============================================================
    // ===================== PAGOS ================================
    // ============================================================

    public boolean registrarPago(Pago pago) {
        if (pago == null || obtenerPago(pago.getIdPago()) != null) return false;
        listaPagos.add(pago);
        return true;
    }

    public Pago obtenerPago(String idPago) {
        for (Pago p : listaPagos) {
            if (p.getIdPago().equals(idPago)) return p;
        }
        return null;
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    // ============================================================
    // ===================== MÉTRICAS / CONSULTAS =================
    // ============================================================

    public long contarEnviosPorEstado(estadoEnvio estado) {
        return listaEnvios.stream().filter(e -> e.getEstado() == estado).count();
    }

    public double calcularIngresosTotales() {
        return listaPagos.stream().mapToDouble(Pago::getMonto).sum();
    }

    public double promedioCostoEnvios() {
        if (listaEnvios.isEmpty()) return 0;
        return listaEnvios.stream().mapToDouble(Envio::getCosto).average().orElse(0);
    }
}
