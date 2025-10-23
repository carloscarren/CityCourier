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
                repartidor.getVehiculoAsignado()
        );
    }

    public static Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto) {
        if (repartidorDto == null) return null;

        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor(repartidorDto.idRepartidor());
        repartidor.setNombre(repartidorDto.nombre());
        repartidor.setTelefono(repartidorDto.telefono());
        repartidor.setVehiculoAsignado(repartidorDto.vehiculoAsignado());
        return repartidor;
    }

    public static List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores) {
        if (listaRepartidores == null) return null;

        List<RepartidorDto> listaDto = new ArrayList<>(listaRepartidores.size());
        for (Repartidor repartidor : listaRepartidores) {
            listaDto.add(repartidorToRepartidorDto(repartidor));
        }
        return listaDto;
    }
}
