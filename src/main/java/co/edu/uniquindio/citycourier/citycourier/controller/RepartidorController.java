package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.Courier;
import co.edu.uniquindio.citycourier.citycourier.domain.CourierAvailability;

import java.util.*;

public class RepartidorController {
    private static final Map<String, Courier> repartidores = new HashMap<>();

    static {
        repartidores.put("R001", new Courier("R001", "Luis Díaz", "1090", "3110000000", "Norte", CourierAvailability.ACTIVO));
        repartidores.put("R002", new Courier("R002", "María Ruiz", "2080", "3121111111", "Sur", CourierAvailability.INACTIVO));
    }

    public static Collection<Courier> listar() { return repartidores.values(); }
    public static Optional<Courier> obtener(String id) { return Optional.ofNullable(repartidores.get(id)); }
    public static Courier crear(String nombre, String documento, String telefono, String zona) {
        String id = "R" + (100 + new Random().nextInt(900));
        Courier c = new Courier(id, nombre, documento, telefono, zona, CourierAvailability.ACTIVO);
        repartidores.put(id, c);
        return c;
    }
    public static void actualizar(Courier c) { repartidores.put(c.getIdRepartidor(), c); }
    public static void eliminar(String id) { repartidores.remove(id); }
}
