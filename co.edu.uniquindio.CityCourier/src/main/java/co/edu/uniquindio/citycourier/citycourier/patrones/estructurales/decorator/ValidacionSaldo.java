package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.decorator;

import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import javafx.scene.control.Alert;

public class ValidacionSaldo extends DecoratorTransaccion {

    public ValidacionSaldo(TransaccionD transaccion){
        super(transaccion);
    }

    @Override
    public void ejecutar() {
         if (transaccion instanceof Transaccion t) {
            double saldoDisponible = t.getCuentaOrigen().getPresupuesto().getMontoPresupuesto();
            double monto = t.getMonto();
            if (saldoDisponible >= monto) {
                super.ejecutar();
                mostrarAlerta("Hecho!", "Saldo válido: " + saldoDisponible);
            } else {
                mostrarAlerta("Error", "El saldo es insuficiente. No se puede realizar la operación");
            }
        } else {
            super.ejecutar();
        }
        
    }

     private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    
}

    



   
    

