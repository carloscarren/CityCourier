package co.edu.uniquindio.citycourier.citycourier.service.envio;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;

import java.util.List;
import java.util.Optional;

public interface EnvioService {
    Envio crear(Envio nuevo);
    Optional<Envio> buscarPorId(String idEnvio);
    List<Envio> listar();
    Envio actualizarEstado(String idEnvio, estadoEnvio nuevoEstado);
    boolean cancelar(String idEnvio);

}
