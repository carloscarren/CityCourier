package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;

public interface ObservadorMetodosAdmin {
    void agregarObserver(ObservadorAdministrador observador);
    void eliminarObserver(ObservadorAdministrador observador);
    void notificarObservers();
}
