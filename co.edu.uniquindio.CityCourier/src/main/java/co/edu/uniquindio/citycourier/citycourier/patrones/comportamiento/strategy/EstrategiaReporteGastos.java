package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaReporteGastos implements EstrategiaEstadistica{

    @Override
    public String getTitulo() {
        return "Gastos personales";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();

        Usuario usuario = usuarios.get(0); // Solo se espera un usuario
        double totalGastos = 0;

        for (Cuenta cuenta : usuario.getListaCuentas()) {
            for (Transaccion transaccion : cuenta.getListaTransacciones()) {
                if (transaccion.getTipoTransaccion() == TipoTransaccion.RETIRO) {
                    totalGastos += transaccion.getMonto();
                }
            }
        }

        resultado.add(new EstadisticaCategoria("Total gastos", totalGastos));
        return resultado;
    }
}
