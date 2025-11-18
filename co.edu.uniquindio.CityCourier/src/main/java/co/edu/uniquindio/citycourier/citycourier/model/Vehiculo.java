package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoVehiculo;

/**
 * Clase que representa un vehículo utilizado por los repartidores del sistema CityCourier.
 * Un vehículo puede estar asignado a un repartidor y tener distintos estados (disponible, en ruta, mantenimiento).
 */
public class Vehiculo {

    private String idVehiculo;
    private String tipoVehiculo; // Moto, Carro, Camión, Bicicleta, etc.
    private String placa;
    private double capacidadMaximaKg;
    private estadoVehiculo estado;

    // ===================== CONSTRUCTORES =====================

    public Vehiculo() {
        this.estado = estadoVehiculo.DISPONIBLE;
    }

    public Vehiculo(String idVehiculo, String tipoVehiculo, String placa, double capacidadMaximaKg, estadoVehiculo estado) {
        this.idVehiculo = idVehiculo;
        this.tipoVehiculo = tipoVehiculo;
        this.placa = placa;
        this.capacidadMaximaKg = capacidadMaximaKg;
        this.estado = estado;
    }

    // ===================== GETTERS Y SETTERS =====================

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getCapacidadMaximaKg() {
        return capacidadMaximaKg;
    }

    public void setCapacidadMaximaKg(double capacidadMaximaKg) {
        this.capacidadMaximaKg = capacidadMaximaKg;
    }

    public estadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(estadoVehiculo estado) {
        this.estado = estado;
    }

    // ===================== MÉTODOS AUXILIARES =====================

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo='" + idVehiculo + '\'' +
                ", tipoVehiculo='" + tipoVehiculo + '\'' +
                ", placa='" + placa + '\'' +
                ", capacidadMaximaKg=" + capacidadMaximaKg +
                ", estado=" + estado +
                '}';
    }
}
