package co.edu.uniquindio.citycourier.citycourier.service.envio;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;

import java.util.*;

public class EnvioServiceMem implements EnvioService {
    private final Map<String, Envio> idEnvioToEnvio = new HashMap<>();
    @Override
    public Envio crear(Envio nuevo) {
       if (nuevo.getIdEnvio() == null || nuevo.getIdEnvio().isEmpty()) {
           nuevo.setIdEnvio(UUID.randomUUID().toString());
       }
       idEnvioToEnvio.put(nuevo.getIdEnvio(), nuevo);
         return nuevo;
    }
    @Override
    public Optional<Envio> buscarPorId(String idEnvio) {
        return Optional.ofNullable(idEnvioToEnvio.get(idEnvio));
    }
    @Override
    public List<Envio> listar () {
        return List.copyOf(idEnvioToEnvio.values());
    }
    @Override
    public Envio actualizarEstado(String idEnvio, estadoEnvio nuevoEstado){
        Envio e = idEnvioToEnvio.get(idEnvio);
        if (e != null) throw new NoSuchElementException("Envio no encontrado:" + idEnvio);
        e.cambiarEstado(nuevoEstado);
        return e;
    }
    @Override
    public boolean cancelar(String idEnvio){
        Envio e = idEnvioToEnvio.get(idEnvio);
        if (e == null) return false;
        if ( e.puedeCancelar()) {
            idEnvioToEnvio.remove(idEnvio);
            return true;
        } else {
            return false;
        }
    }
}
