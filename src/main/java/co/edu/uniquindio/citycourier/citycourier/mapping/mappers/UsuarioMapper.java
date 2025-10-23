package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getTipo(),
                usuario.getDirecciones()
        );
    }

    public static Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioDto.idUsuario());
        usuario.setNombre(usuarioDto.nombre());
        usuario.setCorreo(usuarioDto.correo());
        usuario.setTelefono(usuarioDto.telefono());
        usuario.setTipo(usuarioDto.tipo());
        usuario.setDirecciones(usuarioDto.direcciones());
        return usuario;
    }

    public static List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return null;
        }

        List<UsuarioDto> listaDto = new ArrayList<>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaDto.add(usuarioToUsuarioDto(usuario));
        }
        return listaDto;
    }
}
