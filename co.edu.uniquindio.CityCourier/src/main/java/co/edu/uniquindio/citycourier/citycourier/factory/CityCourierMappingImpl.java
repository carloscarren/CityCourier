package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.EnvioMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.RepartidorMapper;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;
import co.edu.uniquindio.citycourier.citycourier.model.Repartidor;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.service.ICityCourierMapping;

import java.util.List;

public class CityCourierMappingImpl implements ICityCourierMapping {

    // =============== USUARIOS =================
    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> usuarios) {
        return UsuarioMapper.getUsuariosDto(usuarios);
    }

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        return UsuarioMapper.usuarioToUsuarioDto(usuario);
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        return UsuarioMapper.usuarioDtoToUsuario(usuarioDto);
    }

    // =============== REPARTIDORES ===============
    @Override
    public List<RepartidorDto> getRepartidoresDto(List<Repartidor> repartidores) {
        return RepartidorMapper.getRepartidoresDto(repartidores);
    }

    @Override
    public RepartidorDto repartidorToRepartidorDto(Repartidor repartidor) {
        return RepartidorMapper.repartidorToRepartidorDto(repartidor);
    }

    @Override
    public Repartidor repartidorDtoToRepartidor(RepartidorDto dto) {
        return RepartidorMapper.repartidorDtoToRepartidor(dto);
    }

    // =============== ENVIOS =====================
    @Override
    public List<EnvioDto> getEnviosDto(List<Envio> envios) {
        return EnvioMapper.getEnviosDto(envios);
    }

    @Override
    public EnvioDto envioToEnvioDto(Envio envio) {
        return EnvioMapper.envioToEnvioDto(envio);
    }

    @Override
    public Envio envioDtoToEnvio(EnvioDto envioDto) {
        return EnvioMapper.envioDtoToEnvio(envioDto);
    }
}


