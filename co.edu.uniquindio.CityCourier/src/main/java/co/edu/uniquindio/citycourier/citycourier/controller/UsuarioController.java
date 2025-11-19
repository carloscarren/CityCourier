package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.factory.ModelFinanciero;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.Pago;

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
     * También registra el pago asociado al envío en ModelFinanciero (RF-033).
     */
    public EnvioDto crearEnvio(String idUsuario, Direccion origen, Direccion destino,
                                   String descripcion, double peso, double volumen,
                                   double distancia, boolean prioridad) {

        // Cálculo de tarifa simulada
        double costo = cotizar(peso, volumen, distancia, prioridad);

        // Generar ID único para el envío
        String idEnvio = "E" + System.currentTimeMillis();

        // Construcción del DTO del envío
        EnvioDto envio = new EnvioDto(
                idEnvio,
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

        // Registrar el envío en el sistema de logística
        factory.crearEnvio(envio);

        // ============================================================
        // INTEGRACIÓN CON MÓDULO FINANCIERO (RF-033)
        // Registrar el pago asociado al envío en ModelFinanciero
        // ============================================================
        try {
            ModelFinanciero modelFinanciero = ModelFinanciero.getInstancia();

            // Generar ID único para el pago
            String idPago = "PAGO_" + System.currentTimeMillis();

            // Generar número de transacción
            String numeroTransaccion = "TXN_" + System.currentTimeMillis();

            // Crear el objeto Pago asociado al envío
            Pago pago = new Pago(
                    idPago,
                    idEnvio,  // ID del envío asociado
                    costo,    // Monto del pago (costo del envío)
                    "Tarjeta de credito",  // Método de pago por defecto (se puede parametrizar después)
                    numeroTransaccion
            );

            // Procesar el pago (valida y establece el estado)
            pago.procesarPago();

            // Registrar el pago en el modelo financiero
            boolean pagoRegistrado = modelFinanciero.registrarPago(pago);

            if (!pagoRegistrado) {
                System.err.println("Advertencia: No se pudo registrar el pago " + idPago + " para el envío " + idEnvio);
            }
        } catch (Exception e) {
            // Si hay un error al registrar el pago, no fallar la creación del envío
            // pero registrar el error para debugging
            System.err.println("Error al registrar pago para el envío " + idEnvio + ": " + e.getMessage());
            e.printStackTrace();
        }

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
