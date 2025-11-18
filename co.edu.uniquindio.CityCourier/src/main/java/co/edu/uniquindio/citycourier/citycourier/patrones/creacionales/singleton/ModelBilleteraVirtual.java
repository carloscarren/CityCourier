package co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.singleton;

import co.edu.uniquindio.citycourier.citycourier.model.*;

import java.util.ArrayList;
import java.util.List;

public class ModelBilleteraVirtual {

    private static ModelBilleteraVirtual instancia;

    // Simula la base de datos en memoria
    private List<Usuario> listaUsuarios;
    private List<Cuenta> listaCuentas;
    private List<Transaccion> listaTransacciones;
    private List<Presupuesto> listaPresupuestos;
    private List<Categoria> listaCategorias;

    private ModelBilleteraVirtual() {
        // Inicializar listas vacías
        listaUsuarios = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        listaTransacciones = new ArrayList<>();
        listaPresupuestos = new ArrayList<>();
        listaCategorias = new ArrayList<>();
    }

    public static ModelBilleteraVirtual getInstancia() {
        if(instancia == null) {
            instancia = new ModelBilleteraVirtual();
        }
        return instancia;
    }
    public Usuario buscarUsuarioPorId(String idUsuario) {
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }
    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            listaCuentas.add(cuenta);
        }
    }

    public boolean eliminarCuenta(Cuenta cuenta) {
        return listaCuentas.remove(cuenta);
    }

    public boolean eliminarCuentaPorId(String idCuenta) {
        return listaCuentas.removeIf(cuenta -> cuenta.getIdCuenta().equals(idCuenta));
    }

    // Métodos para obtener listas
    public List<Usuario> obtenerUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    public List<Cuenta> obtenerCuentas() {
        return new ArrayList<>(listaCuentas);
    }

    public List<Cuenta> obtenerCuentasPorUsuario(String idUsuario) {
        List<Cuenta> cuentasUsuario = new ArrayList<>();
        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getIdUsuario() != null && cuenta.getIdUsuario().equals(idUsuario)) {
                cuentasUsuario.add(cuenta);
            }
        }
        return cuentasUsuario;
    }

    public List<Transaccion> obtenerTransacciones() {
        return new ArrayList<>(listaTransacciones);
    }

    public List<Transaccion> obtenerTransaccionesPorUsuario(String idUsuario) {
        List<Transaccion> transaccionesUsuario = new ArrayList<>();
        for (Transaccion transaccion : listaTransacciones) {
            if (transaccion.getIdUsuario() != null && transaccion.getIdUsuario().equals(idUsuario)) {
                transaccionesUsuario.add(transaccion);
            }
        }
        return transaccionesUsuario;
    }

    public List<Transaccion> obtenerTransaccionesPorCuenta(String idCuenta) {
        List<Transaccion> transaccionesCuenta = new ArrayList<>();
        for (Transaccion transaccion : listaTransacciones) {
            if (transaccion.getIdCuenta() != null && transaccion.getIdCuenta().equals(idCuenta)) {
                transaccionesCuenta.add(transaccion);
            }
        }
        return transaccionesCuenta;
    }

    public List<Presupuesto> obtenerPresupuestos() {
        return new ArrayList<>(listaPresupuestos);
    }

    public List<Presupuesto> obtenerPresupuestosPorUsuario(String idUsuario) {
        List<Presupuesto> presupuestosUsuario = new ArrayList<>();
        for (Presupuesto presupuesto : listaPresupuestos) {
            if (presupuesto.getIdUsuario() != null && presupuesto.getIdUsuario().equals(idUsuario)) {
                presupuestosUsuario.add(presupuesto);
            }
        }
        return presupuestosUsuario;
    }

    public List<Categoria> obtenerCategorias() {
        return new ArrayList<>(listaCategorias);
    }

    // Métodos para agregar elementos
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario != null && buscarUsuarioPorId(usuario.getIdUsuario()) == null) {
            listaUsuarios.add(usuario);
            return true;
        }
        return false;
    }

    public boolean agregarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            listaTransacciones.add(transaccion);
            return true;
        }
        return false;
    }

    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        if (presupuesto != null) {
            listaPresupuestos.add(presupuesto);
            return true;
        }
        return false;
    }

    public boolean agregarCategoria(Categoria categoria) {
        if (categoria != null) {
            listaCategorias.add(categoria);
            return true;
        }
        return false;
    }

    // Métodos para eliminar elementos
    public boolean eliminarCuenta(String idCuenta) {
        return eliminarCuentaPorId(idCuenta);
    }

    public boolean eliminarPresupuesto(String idPresupuesto) {
        return listaPresupuestos.removeIf(p -> p.getIdPresupuesto().equals(idPresupuesto));
    }

    public boolean eliminarCategoria(String idCategoria) {
        return listaCategorias.removeIf(c -> c.getIdCategoria().equals(idCategoria));
    }

    // Métodos de operaciones financieras (stubs)
    public boolean retirarCuenta(String idCuenta, Double monto, String descripcion, String idCategoria) {
        return false;
    }

    public boolean retirarPresupuesto(String idCuenta, String idPresupuesto, Double monto, String descripcion, String idCategoria) {
        return false;
    }

    public boolean depositoCuenta(String idCuenta, Double monto, String descripcion, String idCategoria) {

        return false;
    }

    public boolean depositoPresupuesto(String idCuenta, String idPresupuesto, Double monto, String descripcion, String idCategoria) {

        return false;
    }

    public boolean realizarTransferencia(String idCuentaOrigen, String idCuentaDestino, Double monto, String descripcion, String idCategoria) {

        return false;
    }
}
