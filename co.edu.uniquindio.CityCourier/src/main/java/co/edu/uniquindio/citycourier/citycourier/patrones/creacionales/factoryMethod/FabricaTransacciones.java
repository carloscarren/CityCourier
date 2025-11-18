package co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;

public class FabricaTransacciones {

    public static Transaccion crear(DatosTransaccion datos) {
        CreadorTransaccion creador = obtenerCreador(datos.tipoTransaccion);
        return creador.crearTransaccion(datos);
    }

    private static CreadorTransaccion obtenerCreador(TipoTransaccion tipo) {
        return switch (tipo) {
            case RETIRO -> new CreadorTransaccionRetiro();
            case DEPOSITO -> new CreadorTransaccionDeposito();
            case TRANSFERENCIA -> new CreadorTransaccionTransferencia();
        };
    }
}
