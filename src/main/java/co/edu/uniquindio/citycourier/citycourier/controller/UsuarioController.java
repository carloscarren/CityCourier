package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador que gestiona las operaciones de los usuarios,
 * como crear envíos, listar envíos y cancelarlos.
 * Actúa como intermediario entre la vista y la lógica de negocio (ModelCityCourier).
 */
public class UsuarioController {

    // Instancia del modelo principal (Singleton)
    private final ModelCityCourier factory = ModelCityCourier.getInstance();

    /**
     * Crea un nuevo envío y lo registra en el sistema.
     */
    public EnvioDto crearEnvio(String idUsuario, Direccion origen, Direccion destino,
                               String descripcion, double peso, double volumen,
                               double distancia, boolean prioridad) {

        // Cálculo de tarifa simulada
        double costo = 5000 + peso * 1000 + volumen * 800 + (prioridad ? 2000 : 0);

        // Construcción del DTO del envío
        EnvioDto envio = new EnvioDto(
                "E" + System.currentTimeMillis(),
                origen.toString(),
                destino.toString(),
                descripcion,
                peso,
                volumen,
                costo,
                LocalDateTime.now().plusHours(4).toString(),
                idUsuario
        );

        factory.crearEnvio(envio);
        return envio;
    }

    /**
     * Retorna la lista de todos los envíos del sistema.
     */
    public List<EnvioDto> listarEnvios() {
        return factory.listarEnvios();
    }

    /**
     * Cancela un envío si aún no ha sido asignado.
     */
    public boolean cancelarEnvio(String idEnvio) {
        return factory.cancelarEnvio(idEnvio);
    }
}