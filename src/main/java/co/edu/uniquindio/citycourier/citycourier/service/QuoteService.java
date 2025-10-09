package co.edu.uniquindio.citycourier.citycourier.service;

import co.edu.uniquindio.citycourier.citycourier.domain.AdditionalService;
import co.edu.uniquindio.citycourier.citycourier.domain.Priority;
import co.edu.uniquindio.citycourier.citycourier.domain.Tariff;

import java.util.EnumSet;

public final class QuoteService {
    private QuoteService() {
    }

    public static double calcularCosto(Tariff tarifa,
                                       double pesoKg,
                                       double volumenM3,
                                       Priority prioridad,
                                       EnumSet<AdditionalService> servicios) {
        double costo = 0.0;
        if (tarifa != null) {
            costo += tarifa.getBase();
            costo += tarifa.getRecargoPeso() * Math.max(0.0, pesoKg);
            costo += tarifa.getRecargoVolumen() * Math.max(0.0, volumenM3);
            if (prioridad == Priority.PRIORITARIA) {
                costo += tarifa.getRecargoPrioridad();
            }
        }
        if (servicios != null) {
            if (servicios.contains(AdditionalService.SEGURO)) {
                costo += 2000;
            }
            if (servicios.contains(AdditionalService.FRAGIL)) {
                costo += 1000;
            }
            if (servicios.contains(AdditionalService.FIRMA_REQUERIDA)) {
                costo += 500;
            }
        }
        return Math.max(0.0, costo);
    }
}
