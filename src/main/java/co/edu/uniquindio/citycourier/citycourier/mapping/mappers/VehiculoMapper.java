package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.VehiculoDto;

public class VehiculoMapper {
    public static VehiculoDto vehiculoToVehiculoDto(Vehiculo vehiculo) {
        if (vehiculo == null) return null;
        return new VehiculoDto(
                vehiculo.getIdVehiculo(),
                vehiculo.getPlaca(),
                vehiculo.getTipo(),
                vehiculo.getCapacidad()
        );
    }
}