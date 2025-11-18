package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;

import java.util.ArrayList;
import java.util.List;

public class EnvioMapper {

    public static EnvioDto envioToEnvioDto(Envio envio) {
        if (envio == null) return null;

        return new EnvioDto(
                envio.getIdEnvio(),
                envio.getOrigen() != null ? envio.getOrigen().toString() : null,
                envio.getDestino() != null ? envio.getDestino().toString() : null,
                envio.getDescripcionPaquete(),
                envio.getPeso(),
                envio.getVolumen(),
                envio.getCosto(),
                envio.getFechaEstimadaEntrega() != null ? envio.getFechaEstimadaEntrega().toString() : null,
                envio.getIdUsuarioAsociado(),
                envio.getEstado() // debe ser de tipo estadoEnvio
        );
    }

    public static Envio envioDtoToEnvio(EnvioDto dto) {
        if (dto == null) return null;

        Envio envio = new Envio();
        envio.setIdEnvio(dto.idEnvio());
        envio.setDescripcionPaquete(dto.descripcion());
        envio.setPeso(dto.peso());
        envio.setVolumen(dto.volumen());
        envio.setCosto(dto.costo());
        envio.setIdUsuarioAsociado(dto.idUsuario());

        if (dto.getEstado() != null) {
            envio.setEstado(dto.getEstado());
        }

        return envio;
    }

    public static List<EnvioDto> getEnviosDto(List<Envio> listaEnvios) {
        if (listaEnvios == null) return null;

        List<EnvioDto> listaDto = new ArrayList<>(listaEnvios.size());
        for (Envio envio : listaEnvios) {
            listaDto.add(envioToEnvioDto(envio));
        }
        return listaDto;
    }
}
