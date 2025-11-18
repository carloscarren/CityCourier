package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.observer;

import java.util.ArrayList;
import java.util.List;

public class UsuarioObservable {

    private static final List<ObservadorAdministrador> observadores = new ArrayList<>();

    public static void agregarObservador(ObservadorAdministrador observador) {
        observadores.add(observador);
    }

    public static void eliminarObservador(ObservadorAdministrador observador) {
        observadores.remove(observador);
    }

    public static void notificarObservadores() {
        for (ObservadorAdministrador obs : observadores) {
            obs.actualizarUsuarios();
        }
    }
}
