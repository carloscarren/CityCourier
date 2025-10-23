package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.*;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.*;
import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.service.ICityCourierService;
import co.edu.uniquindio.citycourier.citycourier.utils.DataUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ✅ Clase Singleton que actúa como Fachada principal del sistema CityCourier.
 * Centraliza la gestión de usuarios, repartidores, envíos y pagos.
 */
public class ModelCityCourier implements ICityCourierService {

    // ============================================================
    // ================== ATRIBUTOS Y SINGLETON ===================
    // ============================================================

    private static ModelCityCourier instance;

    private CityCourier cityCourier;
    private final UsuarioMapper usuarioMapper;
    private final RepartidorMapper repartidorMapper;
    private final EnvioMapper envioMapper;
    private final PagoMapper pagoMapper;

    // 🔒 Constructor privado para el patrón Singleton
    private ModelCityCourier() {
        this.usuarioMapper = new UsuarioMapper();
        this.repartidorMapper = new RepartidorMapper();
        this.envioMapper = new EnvioMapper();
        this.pagoMapper = new PagoMapper();
        inicializarDatos();
    }

    /**
     * Retorna la instancia única del modelo (Singleton).
     */
    public static ModelCityCourier getInstance() {
        if (instance == null) {
            instance = new ModelCityCourier();
        }
        return instance;
    }

    /**
     * Inicializa los datos de prueba usando la clase DataUtil.
     */
    private void inicializarDatos() {
        cityCourier = new CityCourier();
        cityCourier.getListaUsuarios().addAll(DataUtil.crearUsuarios());
        cityCourier.getListaRepartidores().addAll(DataUtil.crearRepartidores());
        cityCourier.getListaEnvios().addAll(DataUtil.crearEnvios(cityCourier.getListaUsuarios()));
    }

    // ============================================================
    // ==================== GESTIÓN DE USUARIOS ===================
    // ============================================================

    public boolean crearUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.usuarioDtoToUsuario(usuarioDto);
        return cityCourier.crearUsuario(usuario);
    }

    public boolean eliminarUsuario(String idUsuario) {
        return cityCourier.eliminarUsuario(idUsuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        return usuarioMapper.getUsuariosDto(cityCourier.getListaUsuarios());
    }

    // Métodos de la interfaz (no usados directamente, pero implementados)
    @Override
    public boolean crearUsuario(String id, String nombre, String correo, String telefono) {
        Usuario usuario = new Usuario(id, nombre, correo, telefono, "1234",
                co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario.CLIENTE);
        return cityCourier.crearUsuario(usuario);
    }

    @Override
    public Usuario obtenerUsuario(String id) {
        return cityCourier.obtenerUsuario(id);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return cityCourier.getListaUsuarios();
    }

    @Override
    public boolean actualizarUsuario(String id, String nombre, String correo, String telefono) {
        Usuario usuario = cityCourier.obtenerUsuario(id);
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setTelefono(telefono);
            return true;
        }
        return false;
    }

    // ============================================================
    // ================== GESTIÓN DE REPARTIDORES =================
    // ============================================================

    public boolean crearRepartidor(RepartidorDto repartidorDto) {
        Repartidor repartidor = repartidorMapper.repartidorDtoToRepartidor(repartidorDto);
        return cityCourier.crearRepartidor(repartidor);
    }

    public List<RepartidorDto> listarRepartidores() {
        return repartidorMapper.getRepartidoresDto(cityCourier.getListaRepartidores());
    }

    @Override
    public boolean crearRepartidor(String id, String nombre, String telefono, String tipoVehiculo) {
        Repartidor repartidor = new Repartidor(id, nombre, telefono, tipoVehiculo, "Centro", new ArrayList<>());
        return cityCourier.crearRepartidor(repartidor);
    }

    @Override
    public Repartidor obtenerRepartidor(String id) {
        return cityCourier.obtenerRepartidor(id);
    }

    @Override
    public List<Repartidor> obtenerRepartidores() {
        return cityCourier.getListaRepartidores();
    }

    // ============================================================
    // ====================== GESTIÓN DE ENVÍOS ===================
    // ============================================================

    public boolean crearEnvio(EnvioDto envioDto) {
        Envio envio = envioMapper.envioDtoToEnvio(envioDto);
        return cityCourier.crearEnvio(envio);
    }

    public boolean cancelarEnvio(String idEnvio) {
        Envio envio = cityCourier.obtenerEnvio(idEnvio);
        if (envio != null && envio.getEstado() == estadoEnvio.SOLICITANDO) {
            envio.setEstado(estadoEnvio.CANCELADO);
            return true;
        }
        return false;
    }

    public List<EnvioDto> listarEnvios() {
        return envioMapper.getEnviosDto(cityCourier.getListaEnvios());
    }

    // Implementación de interfaz
    @Override
    public boolean crearEnvio(String idEnvio, String idUsuario, Direccion origen, Direccion destino,
                              String descripcion, double peso, double volumen, double costo,
                              LocalDateTime fechaEntrega) {
        Envio envio = new Envio(idEnvio, origen, destino, descripcion, peso, volumen, costo,
                co.edu.uniquindio.citycourier.citycourier.model.ENUMS.metodoPago.EFECTIVO,
                fechaEntrega, idUsuario);
        return cityCourier.crearEnvio(envio);
    }

    @Override
    public Envio obtenerEnvio(String idEnvio) {
        return cityCourier.obtenerEnvio(idEnvio);
    }

    @Override
    public List<Envio> obtenerEnvios() {
        return cityCourier.getListaEnvios();
    }

    @Override
    public boolean actualizarEnvio(String idEnvio, String descripcion, double peso, double volumen,
                                   double costo, LocalDateTime fechaEntrega) {
        Envio envio = cityCourier.obtenerEnvio(idEnvio);
        if (envio != null) {
            envio.setDescripcionPaquete(descripcion);
            envio.setPeso(peso);
            envio.setVolumen(volumen);
            envio.setCosto(costo);
            envio.setFechaEstimadaEntrega(fechaEntrega);
            return true;
        }
        return false;
    }

    @Override
    public void eliminarEnvio(String idEnvio) {
        Envio envio = cityCourier.obtenerEnvio(idEnvio);
        if (envio != null) {
            cityCourier.getListaEnvios().remove(envio);
        }
    }

    // ============================================================
    // ======================= GESTIÓN DE PAGOS ===================
    // ============================================================

    public PagoDto registrarPago(PagoDto pagoDto) {
        Pago pago = pagoMapper.pagoDtoToPago(pagoDto);
        pago.procesarPago();
        return pagoMapper.pagoToPagoDto(pago);
    }

    public List<PagoDto> listarPagos() {
        List<PagoDto> pagos = new ArrayList<>();
        for (Envio e : cityCourier.getListaEnvios()) {
            Pago pago = new Pago("PX-" + e.getIdEnvio(), e.getIdEnvio(), e.getCosto(),
                    "Efectivo", "TXN" + System.currentTimeMillis());
            pago.procesarPago();
            pagos.add(pagoMapper.pagoToPagoDto(pago));
        }
        return pagos;
    }
}
