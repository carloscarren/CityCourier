package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.factory.ModelFinanciero;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.Pago;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoPago;

import java.util.ArrayList;
import java.util.List;

/**
 * Estrategia refactorizada para calcular gastos basándose en la entidad Pago.
 * Eliminada la dependencia de Cuentas/Transacciones (sobreingeniería de Billetera Virtual).
 * 
 * Ahora calcula los gastos sumando el monto de los Pagos aprobados asociados a los envíos del usuario.
 */
public class EstrategiaReporteGastos implements EstrategiaEstadistica {

    @Override
    public String getTitulo() {
        return "Gastos personales";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();

        if (usuarios == null || usuarios.isEmpty()) {
            resultado.add(new EstadisticaCategoria("Total gastos", 0.0));
            return resultado;
        }

        Usuario usuario = usuarios.get(0); // Solo se espera un usuario
        String idUsuario = usuario.getIdUsuario();

        if (idUsuario == null || idUsuario.isBlank()) {
            resultado.add(new EstadisticaCategoria("Total gastos", 0.0));
            return resultado;
        }

        // Obtener instancias de los modelos
        ModelFinanciero modelFinanciero = ModelFinanciero.getInstancia();
        ModelCityCourier modelCityCourier = ModelCityCourier.getInstance();

        // Obtener todos los pagos del usuario (asociados a sus envíos)
        List<Pago> pagosUsuario = modelFinanciero.listarPagosPorUsuario(idUsuario, modelCityCourier);

        // Calcular el total de gastos sumando los montos de los pagos aprobados
        double totalGastos = pagosUsuario.stream()
                .filter(p -> p != null && p.getResultado() == estadoPago.APROBADO)
                .mapToDouble(Pago::getMonto)
                .sum();

        resultado.add(new EstadisticaCategoria("Total gastos", totalGastos));
        return resultado;
    }
}
