package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.visitor;

import co.edu.uniquindio.citycourier.citycourier.model.Presupuesto;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

public interface IVisitor {
    void visitar(Usuario usuario);
    void visitar(Presupuesto presupuesto);
    void visitar(Transaccion transaccion);
}