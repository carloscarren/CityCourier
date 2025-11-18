package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.decorator;

public abstract class DecoratorTransaccion implements TransaccionD  {

    protected TransaccionD transaccion;

    public DecoratorTransaccion(TransaccionD transaccion) {
        this.transaccion=transaccion;
    }


    @Override
    public void ejecutar() {
       transaccion.ejecutar();
        
    }

    







   
    
}
