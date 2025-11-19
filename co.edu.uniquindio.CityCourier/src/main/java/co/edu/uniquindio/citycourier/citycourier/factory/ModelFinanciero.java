package co.edu.uniquindio.citycourier.citycourier.factory;

import co.edu.uniquindio.citycourier.citycourier.model.Pago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modelo financiero simplificado para CityCourier.
 * Gestiona únicamente la entidad Pago según los requisitos funcionales:
 * - RF-033: Registrar pagos de envíos
 * - RF-034: Consultar comprobantes de pago
 * - RF-035: Listar pagos por rango de fechas
 * 
 * Patrón Singleton para garantizar una única instancia.
 */
public class ModelFinanciero {

    private static ModelFinanciero instancia;

    // Única lista de pagos (eliminada sobreingeniería de Cuentas/Transacciones/Presupuestos)
    private final List<Pago> listaPagos = new ArrayList<>();

    private ModelFinanciero() {
        // Constructor privado para Singleton
    }

    /**
     * Obtiene la instancia única del modelo financiero (Singleton).
     * 
     * @return Instancia de ModelFinanciero
     */
    public static synchronized ModelFinanciero getInstancia() {
        if (instancia == null) {
            instancia = new ModelFinanciero();
        }
        return instancia;
    }

    /**
     * RF-033: Registra un nuevo pago en el sistema.
     * 
     * @param pago Pago a registrar
     * @return true si se registró correctamente, false si el pago es null o ya existe
     */
    public boolean registrarPago(Pago pago) {
        if (pago == null || pago.getIdPago() == null || pago.getIdPago().isBlank()) {
            return false;
        }

        // Verificar que no exista ya un pago con el mismo ID
        boolean existe = listaPagos.stream()
                .anyMatch(p -> p.getIdPago() != null && p.getIdPago().equals(pago.getIdPago()));

        if (existe) {
            return false;
        }

        listaPagos.add(pago);
        return true;
    }

    /**
     * RF-034: Obtiene un pago por su ID (para consultar comprobantes).
     * 
     * @param idPago ID del pago a buscar
     * @return Pago encontrado o null si no existe
     */
    public Pago obtenerPago(String idPago) {
        if (idPago == null || idPago.isBlank()) {
            return null;
        }

        return listaPagos.stream()
                .filter(p -> p.getIdPago() != null && p.getIdPago().equals(idPago))
                .findFirst()
                .orElse(null);
    }

    /**
     * RF-035: Lista todos los pagos dentro de un rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio del rango (inclusive)
     * @param fechaFin Fecha de fin del rango (inclusive)
     * @return Lista de pagos en el rango especificado
     */
    public List<Pago> listarPagosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return Collections.emptyList();
        }

        return listaPagos.stream()
                .filter(p -> p.getFecha() != null)
                .filter(p -> {
                    LocalDate fechaPago = p.getFecha();
                    return !fechaPago.isBefore(fechaInicio) && !fechaPago.isAfter(fechaFin);
                })
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los pagos asociados a un usuario específico.
     * Útil para reportes de gastos del usuario.
     * 
     * @param idUsuario ID del usuario
     * @param modelCityCourier Instancia de ModelCityCourier para obtener envíos del usuario
     * @return Lista de pagos del usuario
     */
    public List<Pago> listarPagosPorUsuario(String idUsuario, ModelCityCourier modelCityCourier) {
        if (idUsuario == null || idUsuario.isBlank() || modelCityCourier == null) {
            return Collections.emptyList();
        }

        // Obtener todos los envíos del usuario
        var enviosUsuario = modelCityCourier.listarEnvios().stream()
                .filter(e -> e.idUsuario() != null && e.idUsuario().equals(idUsuario))
                .map(e -> e.idEnvio())
                .collect(Collectors.toSet());

        // Filtrar pagos que correspondan a envíos del usuario
        return listaPagos.stream()
                .filter(p -> p.getIdEnvio() != null && enviosUsuario.contains(p.getIdEnvio()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los pagos del sistema (método auxiliar para debugging o reportes administrativos).
     * 
     * @return Lista inmutable de todos los pagos
     */
    public List<Pago> listarTodosLosPagos() {
        return Collections.unmodifiableList(listaPagos);
    }

    /**
     * Obtiene el número total de pagos registrados.
     * 
     * @return Cantidad total de pagos
     */
    public int obtenerCantidadPagos() {
        return listaPagos.size();
    }
}

