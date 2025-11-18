package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.EnvioMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.RepartidorMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.utils.DataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ModelCityCourier {

    private static ModelCityCourier instance;
    private final List<EnvioDto> envios = new ArrayList<>();
    private final List<RepartidorDto> repartidores = new ArrayList<>();
    private final List<UsuarioDto> usuarios = new ArrayList<>();

    private ModelCityCourier() {
        inicializarDatos();
    }

    private void inicializarDatos() {
        // Inicializar usuarios desde DataUtil y convertir a DTOs
        var usuariosModelo = DataUtil.crearUsuarios();
        usuarios.addAll(UsuarioMapper.getUsuariosDto(usuariosModelo));

        // Inicializar repartidores desde DataUtil y convertir a DTOs
        var repartidoresModelo = DataUtil.crearRepartidores();
        repartidores.addAll(RepartidorMapper.getRepartidoresDto(repartidoresModelo));

        // Inicializar envíos desde DataUtil y convertir a DTOs
        var enviosModelo = DataUtil.crearEnvios(usuariosModelo);
        envios.addAll(EnvioMapper.getEnviosDto(enviosModelo));
    }

    public static synchronized ModelCityCourier getInstance() {
        if (instance == null) {
            instance = new ModelCityCourier();
        }
        return instance;
    }

    public void crearEnvio(EnvioDto envio) {
        envios.add(envio);
    }

    public List<EnvioDto> listarEnvios() {
        return Collections.unmodifiableList(envios);
    }

    public boolean cancelarEnvio(String idEnvio) {
        Optional<EnvioDto> opt = envios.stream().filter(e -> e.idEnvio().equals(idEnvio)).findFirst();
        if (opt.isPresent()) {
            EnvioDto e = opt.get();
            if (e.estado() == estadoEnvio.SOLICITANDO) {
                // Los records son inmutables, necesitamos crear uno nuevo
                EnvioDto envioCancelado = new EnvioDto(
                    e.idEnvio(),
                    e.direccionOrigen(),
                    e.direccionDestino(),
                    e.descripcion(),
                    e.peso(),
                    e.volumen(),
                    e.costo(),
                    e.fechaEntrega(),
                    e.idUsuario(),
                    estadoEnvio.CANCELADO
                );
                envios.remove(e);
                envios.add(envioCancelado);
                return true;
            }
        }
        return false;
    }

    public List<RepartidorDto> listarRepartidores() {
        return Collections.unmodifiableList(repartidores);
    }

    public boolean crearRepartidor(RepartidorDto repartidor) {
        if (repartidor == null || repartidor.idRepartidor() == null) {
            return false;
        }
        boolean existe = repartidores.stream()
                .anyMatch(r -> r.idRepartidor().equals(repartidor.idRepartidor()));
        if (!existe) {
            repartidores.add(repartidor);
            return true;
        }
        return false;
    }

    public boolean actualizarEstadoEnvio(String idEnvio, String estadoStr) {
        try {
            estadoEnvio nuevoEstado = estadoEnvio.valueOf(estadoStr.toUpperCase());
            Optional<EnvioDto> opt = envios.stream()
                    .filter(e -> e.idEnvio().equals(idEnvio))
                    .findFirst();
            if (opt.isPresent()) {
                EnvioDto e = opt.get();
                EnvioDto envioActualizado = new EnvioDto(
                        e.idEnvio(),
                        e.direccionOrigen(),
                        e.direccionDestino(),
                        e.descripcion(),
                        e.peso(),
                        e.volumen(),
                        e.costo(),
                        e.fechaEntrega(),
                        e.idUsuario(),
                        nuevoEstado
                );
                envios.remove(e);
                envios.add(envioActualizado);
                return true;
            }
        } catch (IllegalArgumentException e) {
            // Estado inválido
        }
        return false;
    }

    public boolean asignarRepartidorEnvio(String idEnvio, String idRepartidor) {
        Optional<EnvioDto> optEnvio = envios.stream()
                .filter(e -> e.idEnvio().equals(idEnvio))
                .findFirst();
        
        if (optEnvio.isPresent()) {
            EnvioDto e = optEnvio.get();
            if (e.estado() == estadoEnvio.SOLICITANDO || e.estado() == estadoEnvio.ASIGNADO) {
                EnvioDto envioActualizado = new EnvioDto(
                        e.idEnvio(),
                        e.direccionOrigen(),
                        e.direccionDestino(),
                        e.descripcion(),
                        e.peso(),
                        e.volumen(),
                        e.costo(),
                        e.fechaEntrega(),
                        e.idUsuario(),
                        estadoEnvio.EN_RUTA
                );
                envios.remove(e);
                envios.add(envioActualizado);
                return true;
            }
        }
        return false;
    }

    // =============== USUARIOS =================
    public List<UsuarioDto> listarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public boolean crearUsuario(UsuarioDto usuario) {
        if (usuario == null || usuario.idUsuario() == null) {
            return false;
        }
        boolean existe = usuarios.stream()
                .anyMatch(u -> u.idUsuario().equals(usuario.idUsuario()));
        if (!existe) {
            usuarios.add(usuario);
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(String idUsuario) {
        Optional<UsuarioDto> opt = usuarios.stream()
                .filter(u -> u.idUsuario().equals(idUsuario))
                .findFirst();
        if (opt.isPresent()) {
            usuarios.remove(opt.get());
            return true;
        }
        return false;
    }

    /**
     * Busca un usuario por correo y valida su contraseña.
     * @param correo Correo del usuario
     * @param contrasena Contraseña a validar
     * @return UsuarioDto si las credenciales son válidas, null en caso contrario
     */
    public UsuarioDto autenticarUsuario(String correo, String contrasena) {
        if (correo == null || contrasena == null) {
            return null;
        }
        
        for (UsuarioDto usuario : usuarios) {
            if (usuario.correo().equalsIgnoreCase(correo)) {
                // Convertir a modelo para acceder a la contraseña
                var usuarioModelo = UsuarioMapper.usuarioDtoToUsuario(usuario);
                if (usuarioModelo != null && usuarioModelo.getContrasena() != null 
                    && usuarioModelo.getContrasena().equals(contrasena)) {
                    return usuario;
                }
            }
        }
        return null;
    }
}