package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.model.Repartidor;

import java.util.ArrayList;
import java.util.List;

public class RepartidorMapper {

    public static RepartidorDto repartidorToRepartidorDto(Repartidor repartidor) {
        if (repartidor == null) return null;

        return new RepartidorDto(
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getTelefono(),
                repartidor.getVehiculoAsignado(),
                repartidor.getZonaCobertura(),
                repartidor.getEstado()
        );
    }

    public static Repartidor repartidorDtoToRepartidor(RepartidorDto dto) {
        if (dto == null) return null;

        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor(dto.idRepartidor());
        repartidor.setNombre(dto.nombre());
        repartidor.setTelefono(dto.telefono());
        repartidor.setVehiculoAsignado(dto.vehiculoAsignado());
        repartidor.setZonaCobertura(dto.zonaCobertura());
        return repartidor;
    }

    public static List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores) {
        List<RepartidorDto> listaDto = new ArrayList<>();
        if (listaRepartidores != null) {
            for (Repartidor r : listaRepartidores) {
                listaDto.add(repartidorToRepartidorDto(r));
            }
        }
        return listaDto;
    }
}
