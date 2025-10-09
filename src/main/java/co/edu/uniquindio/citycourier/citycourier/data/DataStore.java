package co.edu.uniquindio.citycourier.citycourier.data;

import co.edu.uniquindio.citycourier.citycourier.domain.*;

import java.time.LocalDateTime;
import java.util.*;

public class DataStore {
    private static DataStore instance;

    private final Map<String, User> usuarios;
    private final Map<String, Courier> repartidores;
    private final Map<String, Shipment> envios;
    private final Map<String, Payment> pagos;
    private Tariff tarifaActual;
    private String currentUserId;

    private DataStore() {
        this.usuarios = new HashMap<>();
        this.repartidores = new HashMap<>();
        this.envios = new HashMap<>();
        this.pagos = new HashMap<>();
        seed();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void seed() {
        // Usuarios
        User u1 = new User("U001", "Ana Pérez", "ana@example.com", "3001112222");
        User u2 = new User("U002", "Carlos Gómez", "carlos@example.com", "3003334444");

        Address a1 = new Address("A001", "Casa", "Calle 10 #5-20", "Armenia", 4.534, -75.675);
        Address a2 = new Address("A002", "Oficina", "Cra 14 #23-45", "Armenia", 4.540, -75.670);
        u1.getDireccionesFrecuentes().add(a1);
        u1.getDireccionesFrecuentes().add(a2);

        usuarios.put(u1.getIdUsuario(), u1);
        usuarios.put(u2.getIdUsuario(), u2);

        // Repartidores
        Courier c1 = new Courier("R001", "Luis Díaz", "1090", "3110000000", "Norte", CourierAvailability.ACTIVO);
        Courier c2 = new Courier("R002", "María Ruiz", "2080", "3121111111", "Sur", CourierAvailability.INACTIVO);
        repartidores.put(c1.getIdRepartidor(), c1);
        repartidores.put(c2.getIdRepartidor(), c2);

        // Tarifa base
        tarifaActual = new Tariff(5000, 800, 600, 3000);

        // Envío de ejemplo
        Shipment s1 = new Shipment("E001", a1, a2, 2.0, 0.01, Priority.NORMAL);
        s1.setIdUsuario(u1.getIdUsuario());
        s1.setFechaEstimadaEntrega(LocalDateTime.now().plusHours(6));
        envios.put(s1.getIdEnvio(), s1);

        // Pago ejemplo
        Payment p1 = new Payment("P001", 7500, LocalDateTime.now(), PaymentMethod.TARJETA, PaymentStatus.APROBADO);
        pagos.put(p1.getIdPago(), p1);
    }

    public Map<String, User> getUsuarios() { return usuarios; }
    public Map<String, Courier> getRepartidores() { return repartidores; }
    public Map<String, Shipment> getEnvios() { return envios; }
    public Map<String, Payment> getPagos() { return pagos; }

    public Tariff getTarifaActual() { return tarifaActual; }
    public void setTarifaActual(Tariff tarifaActual) { this.tarifaActual = tarifaActual; }

    public String getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(String currentUserId) { this.currentUserId = currentUserId; }
}
