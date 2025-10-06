package co.edu.uniquindio.citycourier.citycourier.model;

public class Tarifa {
    private String idTarifa;
    private double costoBase;
    private double costoPorPeso;
    private double costoPorVolumen;
    private double costoPorDistancia;
    private double recargoPrioridad;
    private double recargoSeguro;
    private double recargoFragil;
    private double recargoFirma;
    private String zona;
    private boolean activa;

    public Tarifa(){
        this.activa = true;
    }
    public Tarifa (String idTarifa, double costoBase, double costoPorPeso, double costoPorVolumen, double costoPorDistancia, double recargoPrioridad,double recargoSeguro, double recargoFragil, double recargoFirma, String zona){
        this.idTarifa = idTarifa;
        this.costoBase = costoBase;
        this.costoPorPeso = costoPorPeso;
        this.costoPorVolumen = costoPorVolumen;
        this.costoPorDistancia = costoPorDistancia;
        this.recargoPrioridad = 0.0;
        this.recargoSeguro= 0.0;
        this.recargoFragil = 0.0;
        this.recargoFirma = 0.0;
        this.zona = zona;
        this.activa = true;
    }
    public double calcularCosto(double peso, double volumen, double distancia, boolean esPrioritario,boolean esSeguro, boolean esFragil, boolean requiereFirma){
        double costoTotal = costoBase + (costoPorPeso * peso) + (costoPorVolumen * volumen) + (costoPorDistancia * distancia);
        if(esPrioritario){
            costoTotal += recargoPrioridad;
        }
        if(esSeguro){
            costoTotal += recargoSeguro;
        }
        if(esFragil){
            costoTotal += recargoFragil;
        }
        if(requiereFirma){
            costoTotal += recargoFirma;
        }
        return costoTotal;
    }

    public String desglosarTarifa (double peso, double volumen, double distancia, boolean esPrioritario,boolean esSeguro, boolean esFragil, boolean requiereFirma){
        StringBuilder desglose = new StringBuilder();
        desglose.append("Costo Base: ").append(costoBase).append("\n");
        desglose.append("Costo por Peso (").append(peso).append(" kg): ").append(costoPorPeso * peso).append("\n");
        desglose.append("Costo por Volumen (").append(volumen).append(" m³): ").append(costoPorVolumen * volumen).append("\n");
        desglose.append("Costo por Distancia (").append(distancia).append(" km): ").append(costoPorDistancia * distancia).append("\n");
        if(esPrioritario){
            desglose.append("Recargo por Prioridad: ").append(recargoPrioridad).append("\n");
        }
        if(esSeguro){
            desglose.append("Recargo por Seguro: ").append(recargoSeguro).append("\n");
        }
        if(esFragil){
            desglose.append("Recargo por Fragil: ").append(recargoFragil).append("\n");
        }
        if(requiereFirma){
            desglose.append("Recargo por Firma: ").append(recargoFirma).append("\n");
        }
        desglose.append("Costo Total: ").append(calcularCosto(peso, volumen, distancia, esPrioritario, esSeguro, esFragil, requiereFirma)).append("\n");
        return desglose.toString();
    }
    public String getIdTarifa() {
        return idTarifa;
    }
    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }
    public double getCostoBase() {
        return costoBase;
    }
    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }
    public double getCostoPorPeso() {
        return costoPorPeso;
    }
    public void setCostoPorPeso(double costoPorPeso) {
        this.costoPorPeso = costoPorPeso;
    }
    public double getCostoPorVolumen() {
        return costoPorVolumen;
    }
    public void setCostoPorVolumen(double costoPorVolumen) {
        this.costoPorVolumen = costoPorVolumen;
    }
    public double getCostoPorDistancia() {
        return costoPorDistancia;
    }
    public void setCostoPorDistancia(double costoPorDistancia) {
        this.costoPorDistancia = costoPorDistancia;
    }
    public double getRecargoPrioridad() {
        return recargoPrioridad;
    }
    public void setRecargoPrioridad(double recargoPrioridad) {
        this.recargoPrioridad = recargoPrioridad;
    }
    public double getRecargoSeguro() {
        return recargoSeguro;
    }
    public void setRecargoSeguro(double recargoSeguro) {
        this.recargoSeguro = recargoSeguro;
    }
    public double getRecargoFragil() {
        return recargoFragil;
    }
    public void setRecargoFragil(double recargoFragil) {
        this.recargoFragil = recargoFragil;
    }
    public double getRecargoFirma() {
        return recargoFirma;
    }
    public void setRecargoFirma(double recargoFirma) {
        this.recargoFirma = recargoFirma;
    }
    public String getZona() {
        return zona;
    }
    public void setZona(String zona) {
        this.zona = zona;
    }
    public boolean isActiva() {
        return activa;
    }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    @Override
    public String toString() {
        return "Tarifa{" +
                "id='" + idTarifa + '\'' +
                ", zona='" + zona + '\'' +
                ", costoBase=" + costoBase +
                ", activa=" + activa +
                '}';
    }
}