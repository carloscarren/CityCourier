package co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod;

import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;

public class CreadorTransaccionRetiro implements CreadorTransaccion {
    @Override
    public Transaccion crearTransaccion(DatosTransaccion datos) {
        return new Transaccion(
                datos.idTransaccion,
                datos.CuentaOrigen,
                datos.fechaTransaccion,
                datos.monto,
                datos.descripcion,
                null,
                TipoTransaccion.RETIRO
        );
    }
}