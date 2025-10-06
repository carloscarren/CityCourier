package co.edu.uniquindio.citycourier.citycourier.service.tarifa;

public interface TarifaService {
    double cotizar (double peso,double volumen, double distanciaKm, boolean prioridad);
}
