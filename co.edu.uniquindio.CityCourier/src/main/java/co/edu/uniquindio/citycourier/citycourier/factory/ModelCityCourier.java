package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ModelCityCourier {

    private static ModelCityCourier instance;
    private final List<EnvioDto> envios = new ArrayList<>();
    private final List<RepartidorDto> repartidores = new ArrayList<>();
    private final List<UsuarioDto> usuarios = new ArrayList<>();

    private ModelCityCourier() {}

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
}