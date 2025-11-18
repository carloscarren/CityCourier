package co.edu.uniquindio.citycourier.citycourier.service;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;
import co.edu.uniquindio.citycourier.citycourier.model.Repartidor;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.List;

public interface ICityCourierMapping {
    List<UsuarioDto> getUsuariosDto(List<Usuario> usuarios);
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    List<RepartidorDto> getRepartidoresDto(List<Repartidor> repartidores);
    RepartidorDto repartidorToRepartidorDto(Repartidor repartidor);
    Repartidor repartidorDtoToRepartidor(RepartidorDto dto);

    List<EnvioDto> getEnviosDto(List<Envio> envios);
    EnvioDto envioToEnvioDto(Envio envio);
    Envio envioDtoToEnvio(EnvioDto envioDto);
}