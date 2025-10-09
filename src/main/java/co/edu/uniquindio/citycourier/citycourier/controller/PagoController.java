package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PagoController {
    private static final Map<String, Payment> pagos = new HashMap<>();

    public Payment pagar(String idEnvio, PaymentMethod metodo) {
        String id = "P" + (100 + new Random().nextInt(900));
        double monto = EnvioController.obtener(idEnvio).map(Shipment::getCosto).orElse(0.0);
        Payment pago = new Payment(id, monto, LocalDateTime.now(), metodo, PaymentStatus.APROBADO);
        pagos.put(id, pago);
        return pago;
    }

    public Collection<Payment> listarPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return pagos.values().stream()
                .filter(p -> (inicio == null || !p.getFecha().isBefore(inicio)) && (fin == null || !p.getFecha().isAfter(fin)))
                .collect(Collectors.toList());
    }
}
