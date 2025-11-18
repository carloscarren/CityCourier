package co.edu.uniquindio.citycourier.citycourier.model;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto {
    private String idPresupuesto;
    private String idUsuario;
    private double montoPresupuesto;
    private double montoPresupuestoGastado;
    private List<String> listaCategorias;

    public Presupuesto() {
        this.listaCategorias = new ArrayList<>();
        this.montoPresupuestoGastado = 0.0;
    }

    public Presupuesto(String idPresupuesto, double montoPresupuesto) {
        this.idPresupuesto = idPresupuesto;
        this.montoPresupuesto = montoPresupuesto;
        this.montoPresupuestoGastado = 0.0;
        this.listaCategorias = new ArrayList<>();
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public double getMontoPresupuesto() {
        return montoPresupuesto;
    }

    public void setMontoPresupuesto(double montoPresupuesto) {
        this.montoPresupuesto = montoPresupuesto;
    }

    public double getMontoPresupuestoGastado() {
        return montoPresupuestoGastado;
    }

    public void setMontoPresupuestoGastado(double montoPresupuestoGastado) {
        this.montoPresupuestoGastado = montoPresupuestoGastado;
    }

    public List<String> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<String> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}

