package co.edu.uniquindio.citycourier.citycourier.service;

import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;
import co.edu.uniquindio.citycourier.citycourier.model.Repartidor;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public interface ICityCourierService {

    boolean crearUsuario(String id, String nombre, String correo, String telefono);
    Usuario obtenerUsuario(String id);
    List<Usuario> obtenerUsuarios();
    boolean actualizarUsuario(String id, String nombre, String correo, String telefono);
    boolean eliminarUsuario(String id);


    boolean crearEnvio(String idEnvio, String idUsuario, Direccion origen, Direccion destino, String descripcion, double peso, double volumen, double costo, LocalDateTime fechaEntrega);
    Envio obtenerEnvio(String idEnvio);
    List<Envio> obtenerEnvios();
    boolean actualizarEnvio(String idEnvio, String descripcion, double peso, double volumen, double costo, LocalDateTime fechaEntrega);
    void eliminarEnvio(String idEnvio);


    boolean crearRepartidor(String id, String nombre, String telefono, String tipoVehiculo);
    Repartidor obtenerRepartidor(String id);
    List<Repartidor> obtenerRepartidores();
}