package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;

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
        double costo = cotizar(peso, volumen, distancia, prioridad);

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
                idUsuario,
                estadoEnvio.SOLICITANDO
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

    /**
     * Calcula una cotización estimada según peso, volumen y distancia.
     * Utiliza el modelo Tarifa para calcular el costo real.
     */
    public double cotizar(double peso, double volumen, double distancia, boolean prioridad) {
        // Crear una tarifa estándar para el cálculo
        co.edu.uniquindio.citycourier.citycourier.model.Tarifa tarifa = 
            new co.edu.uniquindio.citycourier.citycourier.model.Tarifa(
                "TARIFA_STD",
                5000,  // costoBase
                1000,  // costoPorPeso
                800,   // costoPorVolumen
                500,   // costoPorDistancia
                2000,  // recargoPrioridad
                0,     // recargoSeguro
                0,     // recargoFragil
                0,     // recargoFirma
                "GENERAL" // zona
            );

        // Usar el método calcularCosto del modelo Tarifa
        return tarifa.calcularCosto(peso, volumen, distancia, prioridad, false, false, false);
    }
}
