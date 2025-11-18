package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;

import java.util.List;

public class AdministradorController {

    private final ModelCityCourier model = ModelCityCourier.getInstance();

    public List<EnvioDto> listarEnvios() {
        return model.listarEnvios();
    }

    public List<RepartidorDto> listarRepartidores() {
        return model.listarRepartidores();
    }

    public boolean actualizarEstadoEnvio(String idEnvio, String estado) {
        return model.actualizarEstadoEnvio(idEnvio, estado);
    }

    public boolean asignarRepartidor(String idEnvio, String idRepartidor) {
        return model.asignarRepartidorEnvio(idEnvio, idRepartidor);
    }
}
