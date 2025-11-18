package co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod;

import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;

public interface CreadorTransaccion {
    Transaccion crearTransaccion(DatosTransaccion datos);
}