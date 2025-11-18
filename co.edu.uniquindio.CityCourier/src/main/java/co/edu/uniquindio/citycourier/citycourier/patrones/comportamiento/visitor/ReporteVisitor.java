package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.visitor;

import co.edu.uniquindio.citycourier.citycourier.model.Presupuesto;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

public class ReporteVisitor implements IVisitor {
    @Override
    public void visitar(Usuario usuario) {
        double saldoTotal = 0.0;
        if (usuario.getListaCuentas() != null) {
            for (var cuenta : usuario.getListaCuentas()) {
                saldoTotal += cuenta.getSaldoActual();
            }
        }
        System.out.println("Usuario: " + usuario.getIdUsuario() + " | Saldo: $" + saldoTotal);
    }

    @Override
    public void visitar(Presupuesto presupuesto) {
        System.out.println("Presupuesto: " + presupuesto.getListaCategorias() +
                " | Total: $" + presupuesto.getMontoPresupuesto());
    }

    @Override
    public void visitar(Transaccion transaccion) {
        System.out.println("Transacción: " + transaccion.getDescripcion() + " | Monto: $" + transaccion.getMonto());
    }
}