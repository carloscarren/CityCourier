package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;

import co.edu.uniquindio.citycourier.citycourier.model.Tarifa;

public interface Observador {
    void actualizar(Tarifa tarifa);
}
