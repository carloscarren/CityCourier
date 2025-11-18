package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaReporteIngresos implements EstrategiaEstadistica {

    @Override
    public String getTitulo() {
        return "Ingresos personales";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();
        Usuario usuario = usuarios.get(0); // Solo se espera uno

        double totalIngresos = 0;
        for (Cuenta cuenta : usuario.getListaCuentas()) {
            for (Transaccion transaccion : cuenta.getListaTransacciones()) {
                if (transaccion.getTipoTransaccion() == TipoTransaccion.DEPOSITO) {
                    totalIngresos += transaccion.getMonto();
                }
            }
        }

        resultado.add(new EstadisticaCategoria("Total ingresos", totalIngresos));
        return resultado;
    }
}
