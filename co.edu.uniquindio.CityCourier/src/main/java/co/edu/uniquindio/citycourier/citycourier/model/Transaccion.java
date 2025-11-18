package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.NombreCategoria;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;

import java.time.LocalDate;

public class Transaccion {
    private String idTransaccion;
    private Cuenta cuentaOrigen;
    private LocalDate fechaTransaccion;
    private double monto;
    private String descripcion;
    private Cuenta cuentaDestino;
    private TipoTransaccion tipoTransaccion;
    private NombreCategoria categoriaProcesada;

    public Transaccion() {
    }

    public Transaccion(String idTransaccion, Cuenta cuentaOrigen, LocalDate fechaTransaccion,
                       double monto, String descripcion, Cuenta cuentaDestino, TipoTransaccion tipoTransaccion) {
        this.idTransaccion = idTransaccion;
        this.cuentaOrigen = cuentaOrigen;
        this.fechaTransaccion = fechaTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaDestino = cuentaDestino;
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public NombreCategoria getCategoriaProcesada() {
        return categoriaProcesada;
    }

    public void setCategoriaProcesada(NombreCategoria categoriaProcesada) {
        this.categoriaProcesada = categoriaProcesada;
    }

    public String getIdUsuario() {
        if (cuentaOrigen != null && cuentaOrigen.getUsuarioAsociado() != null) {
            return cuentaOrigen.getUsuarioAsociado().getIdUsuario();
        }
        return null;
    }

    public String getIdCuenta() {
        if (cuentaOrigen != null) {
            return cuentaOrigen.getIdCuenta();
        }
        return null;
    }
}

