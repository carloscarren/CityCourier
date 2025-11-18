package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.NombreCategoria;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaGastosPorCategoria implements EstrategiaEstadistica {
    @Override
    public String getTitulo() {
        return "Gastos más comunes por categoría";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            for (Cuenta cuenta : usuario.getListaCuentas()) {
                for (Transaccion transaccion : cuenta.getListaTransacciones()) {
                    NombreCategoria categoria = transaccion.getCategoriaProcesada();
                    if (categoria != null) {
                        String nombreCategoria = categoria.name(); // O también: categoria.toString();
                        double monto = transaccion.getMonto();

                        boolean encontrada = false;
                        for (EstadisticaCategoria ec : resultado) {
                            if (ec.getNombre().equals(nombreCategoria)) {
                                ec.sumarValor(monto);
                                encontrada = true;
                                break;
                            }
                        }

                        if (!encontrada) {
                            resultado.add(new EstadisticaCategoria(nombreCategoria, monto));
                        }
                    }
                }
            }
        }
        return resultado;

    }
}
