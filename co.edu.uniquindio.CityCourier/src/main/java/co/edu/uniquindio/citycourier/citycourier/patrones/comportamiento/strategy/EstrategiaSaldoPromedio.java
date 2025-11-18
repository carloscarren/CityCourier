package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.*;


import java.util.ArrayList;
import java.util.List;

public class EstrategiaSaldoPromedio implements EstrategiaEstadistica{

    @Override
    public String getTitulo() {
        return "Saldo promedio de usuarios";
    }

    @Override
    public List<EstadisticaCategoria> calcular(List<Usuario> usuarios) {
        double sumaSaldosUsuarios = 0;
        int usuariosValidos = 0;

        for (Usuario usuario : usuarios) {
            List<Cuenta> cuentas = usuario.getListaCuentas();
            if (cuentas == null || cuentas.isEmpty()) continue;

            double saldoTotalUsuario = 0;
            boolean tienePresupuesto = false;

            for (Cuenta cuenta : cuentas) {
                if (cuenta.getPresupuesto() != null) {
                    double saldo = cuenta.getPresupuesto().getMontoPresupuesto()
                            - cuenta.getPresupuesto().getMontoPresupuestoGastado();
                    saldoTotalUsuario += saldo;
                    tienePresupuesto = true;
                }
            }

            if (tienePresupuesto) {
                sumaSaldosUsuarios += saldoTotalUsuario;
                usuariosValidos++;
            }
        }

        double promedio = (usuariosValidos > 0) ? sumaSaldosUsuarios / usuariosValidos : 0;

        List<EstadisticaCategoria> resultado = new ArrayList<>();
        resultado.add(new EstadisticaCategoria("Saldo promedio por usuario", promedio));
        return resultado;
    }

}
