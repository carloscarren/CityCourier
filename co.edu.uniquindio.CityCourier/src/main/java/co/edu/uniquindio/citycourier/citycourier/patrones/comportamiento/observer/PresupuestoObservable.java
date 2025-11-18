package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;

import co.edu.uniquindio.citycourier.citycourier.model.Tarifa;

import java.util.ArrayList;
import java.util.List;

public class PresupuestoObservable implements ObservadorMetodos {

    private List<Observador> observadores = new ArrayList<>();
    private Tarifa tarifa;

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
        notificarObservers();
    }
    
    public Tarifa getTarifa() {
        return tarifa;
    }

    @Override
    public void agregarObserver(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObserver(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservers() {
        for (Observador observador : observadores) {
            observador.actualizar(tarifa);
        }
    }

    
}
