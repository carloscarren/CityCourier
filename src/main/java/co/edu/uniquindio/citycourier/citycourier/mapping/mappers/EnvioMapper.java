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
                envio.getOrigen().toString(),
                envio.getDestino().toString(),
                envio.getEstado().toString(),
                envio.getFechaCreacion().toLocalDate(),
                envio.getCosto()
        );
    }

    public static Envio envioDtoToEnvio(EnvioDto dto) {
        if (dto == null) return null;

        Envio envio = new Envio();
        envio.setIdEnvio(dto.idEnvio());
        envio.setCosto(dto.costo());
        // ⚠️ No reconstruimos Direccion aquí porque los DTO guardan solo Strings
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
