package co.edu.uniquindio.citycourier.citycourier.domain;

public class Tariff {
    private double base;
    private double recargoPeso;
    private double recargoVolumen;
    private double recargoPrioridad;

    public Tariff() {
    }

    public Tariff(double base, double recargoPeso, double recargoVolumen, double recargoPrioridad) {
        this.base = base;
        this.recargoPeso = recargoPeso;
        this.recargoVolumen = recargoVolumen;
        this.recargoPrioridad = recargoPrioridad;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getRecargoPeso() {
        return recargoPeso;
    }

    public void setRecargoPeso(double recargoPeso) {
        this.recargoPeso = recargoPeso;
    }

    public double getRecargoVolumen() {
        return recargoVolumen;
    }

    public void setRecargoVolumen(double recargoVolumen) {
        this.recargoVolumen = recargoVolumen;
    }

    public double getRecargoPrioridad() {
        return recargoPrioridad;
    }

    public void setRecargoPrioridad(double recargoPrioridad) {
        this.recargoPrioridad = recargoPrioridad;
    }
}
