package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.decorator;

import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;

public class TransaccionConNotificacion extends DecoratorTransaccion {

    public TransaccionConNotificacion(TransaccionD transaccion){
        super(transaccion);
    }

    @Override
    public void ejecutar() {
        System.out.println("Decorador de notificación ejecutado");
        super.ejecutar();
        if (transaccion instanceof Transaccion t) {
            String mensaje = 
                    "\nTipo: " + t.getTipoTransaccion() +
                    "\nMonto: $" + t.getMonto() +
                    "\nFecha: " + t.getFechaTransaccion() +
                    "\nDescripción: " + t.getDescripcion();

            // Simulación en consola (sin correo)
            System.out.println("=== NOTIFICACIÓN DE TRANSACCIÓN ===");
            System.out.println("Para: simulado");
            System.out.println("Asunto: Transacción realizada");
            System.out.println("Mensaje:" + mensaje);
            System.out.println("===================================");
        }
    }
}
