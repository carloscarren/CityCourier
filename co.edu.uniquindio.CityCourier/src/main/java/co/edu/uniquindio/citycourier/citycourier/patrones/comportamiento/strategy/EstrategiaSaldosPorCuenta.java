package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EstrategiaSaldosPorCuenta implements EstrategiaEstadistica{
    @Override
    public String getTitulo() {
        return "Saldos por cuenta";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        List<EstadisticaCategoria> resultado = new ArrayList<>();

        Usuario usuario = usuarios.get(0); // Solo se espera un usuario

        for (Cuenta cuenta : usuario.getListaCuentas()) {
            if (cuenta.getPresupuesto() != null) {
                double saldo = cuenta.getPresupuesto().getMontoPresupuesto()
                        - cuenta.getPresupuesto().getMontoPresupuestoGastado();
                resultado.add(new EstadisticaCategoria("Cuenta " + cuenta.getNumeroCuenta(), saldo));
            }
        }

        return resultado;
    }
}
