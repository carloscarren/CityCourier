package co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;

public class CreadorTransaccionDeposito implements CreadorTransaccion {
    @Override
    public Transaccion crearTransaccion(DatosTransaccion datos) {
        return new Transaccion(
            datos.idTransaccion,
            null,
            datos.fechaTransaccion,
            datos.monto,
            datos.descripcion,
            datos.CuentaDestino,
            TipoTransaccion.DEPOSITO
        );
    }
}

