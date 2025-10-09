package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.Courier;

import java.util.Collection;
import java.util.Random;

public class RepartidorController {
    public Collection<Courier> listar() {
        return DataStore.getInstance().getRepartidores().values();
    }

    public Courier crear(String nombre, String documento, String telefono, String zona) {
        String id = "R" + (100 + new Random().nextInt(900));
        Courier c = new Courier(id, nombre, documento, telefono, zona, co.edu.uniquindio.citycourier.citycourier.domain.CourierAvailability.ACTIVO);
        DataStore.getInstance().getRepartidores().put(id, c);
        return c;
    }

    public void actualizar(Courier c) {
        DataStore.getInstance().getRepartidores().put(c.getIdRepartidor(), c);
    }

    public void eliminar(String id) {
        DataStore.getInstance().getRepartidores().remove(id);
    }
}
