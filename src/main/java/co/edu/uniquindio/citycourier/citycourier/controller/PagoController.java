package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

public class PagoController {
    public Payment pagar(String idEnvio, PaymentMethod metodo) {
        String id = "P" + (100 + new Random().nextInt(900));
        double monto = DataStore.getInstance().getEnvios().get(idEnvio).getCosto();
        Payment pago = new Payment(id, monto, LocalDateTime.now(), metodo, PaymentStatus.APROBADO);
        DataStore.getInstance().getPagos().put(id, pago);
        return pago;
    }

    public Collection<Payment> listarPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return DataStore.getInstance().getPagos().values().stream()
                .filter(p -> (inicio == null || !p.getFecha().isBefore(inicio)) && (fin == null || !p.getFecha().isAfter(fin)))
                .collect(Collectors.toList());
    }
}
