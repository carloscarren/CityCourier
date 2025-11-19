package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.EnvioMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.RepartidorMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
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
    // Lista de usuarios del modelo para autenticación (mantiene las contraseñas)
    private final List<Usuario> usuariosModelo = new ArrayList<>();
    // Lista de ciudades (formato: "ID | Nombre | Habitantes")
    private final List<String> ciudades = new ArrayList<>();

    private ModelCityCourier() {
        inicializarDatos();
    }

    private void inicializarDatos() {
        // Inicializar usuarios desde DataUtil y convertir a DTOs
        var usuariosModelo = DataUtil.crearUsuarios();
        this.usuariosModelo.addAll(usuariosModelo);
        usuarios.addAll(UsuarioMapper.getUsuariosDto(usuariosModelo));

        // Inicializar repartidores desde DataUtil y convertir a DTOs
        var repartidoresModelo = DataUtil.crearRepartidores();
        repartidores.addAll(RepartidorMapper.getRepartidoresDto(repartidoresModelo));

        // Inicializar envíos desde DataUtil y convertir a DTOs
        var enviosModelo = DataUtil.crearEnvios(usuariosModelo);
        envios.addAll(EnvioMapper.getEnviosDto(enviosModelo));
        
        // Inicializar ciudades por defecto
        ciudades.add("634020 | Quimbaya | 32175");
        ciudades.add("630001 | Armenia | 309474");
        ciudades.add("631001 | Circasia | 29789");
        ciudades.add("633020 | Tebaida | 35000");
        ciudades.add("633001 | Montenegro | 38460");
        ciudades.add("660001 | Pereira | 467185");
        ciudades.add("170001 | Manizales | 434403");
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
    
    public boolean actualizarRepartidor(RepartidorDto repartidorActualizado) {
        if (repartidorActualizado == null || repartidorActualizado.idRepartidor() == null) {
            return false;
        }
        Optional<RepartidorDto> opt = repartidores.stream()
                .filter(r -> r.idRepartidor().equals(repartidorActualizado.idRepartidor()))
                .findFirst();
        if (opt.isPresent()) {
            int index = repartidores.indexOf(opt.get());
            repartidores.set(index, repartidorActualizado);
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
                        nuevoEstado,
                        e.idRepartidor() // Preservar el repartidor asignado
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
                        estadoEnvio.EN_RUTA,
                        idRepartidor
                );
                envios.remove(e);
                envios.add(envioActualizado);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene el repartidor asignado a un envío
     * @param idEnvio ID del envío
     * @return ID del repartidor asignado, o null si no tiene asignado
     */
    public String obtenerRepartidorAsignado(String idEnvio) {
        return envios.stream()
                .filter(e -> e.idEnvio().equals(idEnvio))
                .map(e -> e.idRepartidor())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Obtiene todos los envíos asignados a un repartidor
     * @param idRepartidor ID del repartidor
     * @return Lista de envíos asignados
     */
    public List<EnvioDto> obtenerEnviosAsignados(String idRepartidor) {
        return envios.stream()
                .filter(e -> idRepartidor != null && idRepartidor.equals(e.idRepartidor()))
                .toList();
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
            // También eliminar de usuariosModelo
            usuariosModelo.removeIf(u -> u.getIdUsuario().equals(idUsuario));
            return true;
        }
        return false;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Guarda tanto en usuariosModelo (para autenticación) como en usuarios (DTOs).
     * @param usuario Usuario con contraseña a registrar
     * @return true si se registró correctamente, false si ya existe
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            return false;
        }

        // Verificar que no exista ya el ID
        boolean existeId = usuariosModelo.stream()
                .anyMatch(u -> u.getIdUsuario() != null && u.getIdUsuario().equals(usuario.getIdUsuario()));
        if (existeId) {
            return false;
        }

        // Verificar que no exista ya el correo
        String correoNormalizado = usuario.getCorreo() != null ? usuario.getCorreo().trim().toLowerCase() : null;
        boolean existeCorreo = usuariosModelo.stream()
                .anyMatch(u -> u.getCorreo() != null && 
                        u.getCorreo().trim().toLowerCase().equals(correoNormalizado));
        if (existeCorreo) {
            return false;
        }

        // Agregar a usuariosModelo (con contraseña)
        usuariosModelo.add(usuario);

        // Convertir a DTO y agregar a usuarios
        UsuarioDto usuarioDto = UsuarioMapper.usuarioToUsuarioDto(usuario);
        if (usuarioDto != null) {
            usuarios.add(usuarioDto);
        }

        return true;
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
        
        // Normalizar el correo (trim y lowercase)
        String correoNormalizado = correo.trim().toLowerCase();
        // Normalizar la contraseña (solo trim, sin cambiar mayúsculas/minúsculas)
        String contrasenaNormalizada = contrasena.trim();
        
        // Buscar en los usuarios del modelo que tienen las contraseñas
        for (Usuario usuarioModelo : usuariosModelo) {
            if (usuarioModelo != null && usuarioModelo.getCorreo() != null) {
                String correoUsuario = usuarioModelo.getCorreo().trim().toLowerCase();
                if (correoUsuario.equals(correoNormalizado)) {
                    // Verificar contraseña (comparación exacta después de trim)
                    String contrasenaUsuario = usuarioModelo.getContrasena();
                    if (contrasenaUsuario != null && contrasenaUsuario.trim().equals(contrasenaNormalizada)) {
                        // Si las credenciales son correctas, buscar el DTO correspondiente
                        String idUsuario = usuarioModelo.getIdUsuario();
                        if (idUsuario != null) {
                            return usuarios.stream()
                                    .filter(u -> u.idUsuario() != null && u.idUsuario().equals(idUsuario))
                                    .findFirst()
                                    .orElse(null);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Obtiene la lista de usuarios como entidades del modelo (no DTOs).
     * Útil para reportes que requieren objetos Usuario completos.
     * @return Lista inmutable de usuarios del modelo
     */
    public List<Usuario> obtenerUsuariosEntidad() {
        return Collections.unmodifiableList(usuariosModelo);
    }

    /**
     * Obtiene un usuario como entidad del modelo por su ID.
     * @param idUsuario ID del usuario a buscar
     * @return Usuario si existe, null en caso contrario
     */
    public Usuario obtenerUsuarioEntidad(String idUsuario) {
        if (idUsuario == null) {
            return null;
        }
        return usuariosModelo.stream()
                .filter(u -> u.getIdUsuario() != null && u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }
    
    // =============== CIUDADES =================
    
    /**
     * Obtiene la lista de ciudades
     * @return Lista inmutable de ciudades (formato: "ID | Nombre | Habitantes")
     */
    public List<String> listarCiudades() {
        return Collections.unmodifiableList(ciudades);
    }
    
    /**
     * Obtiene solo los nombres de las ciudades (sin ID ni habitantes)
     * @return Lista de nombres de ciudades
     */
    public List<String> obtenerNombresCiudades() {
        return ciudades.stream()
                .map(ciudad -> {
                    if (ciudad != null && ciudad.contains(" | ")) {
                        String[] partes = ciudad.split(" \\| ");
                        if (partes.length > 1) {
                            return partes[1].trim();
                        }
                    }
                    return ciudad;
                })
                .filter(nombre -> nombre != null && !nombre.isBlank())
                .distinct()
                .toList();
    }
    
    /**
     * Agrega una nueva ciudad
     * @param ciudad Ciudad en formato "ID | Nombre | Habitantes"
     * @return true si se agregó correctamente, false si ya existe
     */
    public boolean agregarCiudad(String ciudad) {
        if (ciudad == null || ciudad.isBlank()) {
            return false;
        }
        // Verificar si ya existe una ciudad con el mismo ID
        if (ciudad.contains(" | ")) {
            String id = ciudad.split(" \\| ")[0].trim();
            boolean existe = ciudades.stream()
                    .anyMatch(c -> c != null && c.startsWith(id + " | "));
            if (existe) {
                return false;
            }
        }
        ciudades.add(ciudad);
        return true;
    }
}