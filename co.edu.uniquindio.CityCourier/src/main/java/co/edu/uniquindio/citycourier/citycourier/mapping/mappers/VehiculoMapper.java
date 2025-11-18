package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.VehiculoDto;
import co.edu.uniquindio.citycourier.citycourier.model.Vehiculo;

public class VehiculoMapper {

    public static VehiculoDto vehiculoToVehiculoDto(Vehiculo vehiculo) {
        if (vehiculo == null) return null;

        return new VehiculoDto(
                vehiculo.getIdVehiculo(),
                vehiculo.getPlaca(),
                vehiculo.getTipoVehiculo(),
                vehiculo.getCapacidadMaximaKg()
        );
    }

    public static Vehiculo vehiculoDtoToVehiculo(VehiculoDto dto) {
        if (dto == null) return null;

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(dto.idVehiculo());
        vehiculo.setPlaca(dto.placa());
        vehiculo.setTipoVehiculo(dto.tipo());
        vehiculo.setCapacidadMaximaKg(dto.capacidad());

        return vehiculo;
    }
}
