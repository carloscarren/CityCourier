package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper para convertir entre Usuario y UsuarioDto.
 * Se encarga de la transformación de entidades del dominio a DTOs y viceversa.
 */
public class UsuarioMapper {
    /**
     * Convierte un UsuarioDto a Usuario.
     */
    public static Usuario usuarioDtoToUsuario(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.idUsuario());
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setTelefono(dto.telefono());
        usuario.setTipo(dto.tipo());
        usuario.setDireccionesFrecuentes(
                dto.direcciones() != null ? mapStringsToDirecciones(dto.direcciones()) : new ArrayList<>()
        );

        return usuario;
    }

    public static List<UsuarioDto> getUsuariosDto(List<Usuario> usuarios) {
        List<UsuarioDto> listaDto = new ArrayList<>();
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                listaDto.add(usuarioToUsuarioDto(u));
            }
        }
        return listaDto;
    }

    public static List<Usuario> getUsuarios(List<UsuarioDto> usuariosDto) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        if (usuariosDto != null) {
            for (UsuarioDto dto : usuariosDto) {
                listaUsuarios.add(usuarioDtoToUsuario(dto));
            }
        }
        return listaUsuarios;
    }
    public static UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        List<String> direcciones = usuario.getDireccionesFrecuentes() != null
                ? mapDireccionesToStrings(usuario.getDireccionesFrecuentes())
                : new ArrayList<>();


        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getTipo(),
                direcciones
        );
    }
    private static List<Direccion> mapStringsToDirecciones(List<String> strs) {
        List<Direccion> result = new ArrayList<>();
        if (strs == null) return result;

        for (String s : strs) {
            try {
                // Intentar constructor Direccion(String)
                Constructor<Direccion> ctor = Direccion.class.getDeclaredConstructor(String.class);
                ctor.setAccessible(true);
                Direccion d = ctor.newInstance(s);
                result.add(d);
                continue;
            } catch (Exception ignored) {
            }

            try {
                Direccion d = Direccion.class.getDeclaredConstructor().newInstance();
                Method setter = Direccion.class.getMethod("setDireccion", String.class);
                setter.invoke(d, s);
                result.add(d);
                continue;
            } catch (Exception ignored) {
            }

            try {
                Direccion d = Direccion.class.getDeclaredConstructor().newInstance();
                result.add(d);
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    private static List<String> mapDireccionesToStrings(List<Direccion> dirs) {
        List<String> result = new ArrayList<>();
        if (dirs == null) return result;

        for (Direccion d : dirs) {
            if (d == null) {
                result.add(null);
                continue;
            }
            String val = null;
            try {
                Method getter = Direccion.class.getMethod("getDireccion");
                Object out = getter.invoke(d);
                val = out != null ? out.toString() : null;
            } catch (Exception ignored) {
            }

            if (val == null) {
                try {
                    Method getter2 = Direccion.class.getMethod("getDetalle");
                    Object out = getter2.invoke(d);
                    val = out != null ? out.toString() : null;
                } catch (Exception ignored) {
                }
            }

            if (val == null) {
                val = d.toString();
            }
            result.add(val);
        }
        return result;
    }
}
