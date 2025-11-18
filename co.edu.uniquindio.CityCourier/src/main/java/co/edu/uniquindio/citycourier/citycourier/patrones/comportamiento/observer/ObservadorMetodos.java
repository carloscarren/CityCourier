package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;

public interface ObservadorMetodos {
    void agregarObserver(Observador observador);
    void eliminarObserver(Observador observador);
    void notificarObservers();
}
