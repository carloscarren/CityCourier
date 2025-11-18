package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaUsuariosConMasTransacciones implements EstrategiaEstadistica {

    @Override
    public String getTitulo() {
        return "Usuarios con más transacciones";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();

        for (Usuario u : usuarios) {
            int total = 0;
            for (Cuenta cuenta : u.getListaCuentas()) {
                total += cuenta.getListaTransacciones().size();
            }

            System.out.println(u.getNombre() + " tiene " + total + " transacciones.");

            if (total > 0) {
                resultado.add(new EstadisticaCategoria(u.getNombre(), total));
            }
        }

        return resultado;
    }
}
