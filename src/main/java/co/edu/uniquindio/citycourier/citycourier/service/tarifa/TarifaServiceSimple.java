package co.edu.uniquindio.citycourier.citycourier.service.tarifa;

public class TarifaServiceSimple implements TarifaService {
    private final double base = 5000;
    private final double porKg = 1200;
    private final double porM3 = 8000;
    private final double porKm = 400;
    private final double recargoPrioridad = 3000;

    @Override
    public double cotizar(double peso, double volumen, double distanciaKm, boolean prioridad) {
        double total = base + (peso*porKg) + (volumen*porM3) + (distanciaKm*recargoPrioridad);
        if (prioridad) total += recargoPrioridad;
        return Math.round(total);
    }
}
