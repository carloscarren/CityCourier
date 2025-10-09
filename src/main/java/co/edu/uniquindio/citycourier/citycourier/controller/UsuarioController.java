package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.Address;
import co.edu.uniquindio.citycourier.citycourier.domain.User;

import java.util.*;

public class UsuarioController {
    private static final Map<String, User> usuarios = new HashMap<>();

    static {
        // Seed básico
        User u1 = new User("U001", "Ana Pérez", "ana@example.com", "3001112222");
        User u2 = new User("U002", "Carlos Gómez", "carlos@example.com", "3003334444");
        u1.getDireccionesFrecuentes().add(new Address("A001", "Casa", "Calle 10 #5-20", "Armenia", 4.534, -75.675));
        u1.getDireccionesFrecuentes().add(new Address("A002", "Oficina", "Cra 14 #23-45", "Armenia", 4.540, -75.670));
        usuarios.put(u1.getIdUsuario(), u1);
        usuarios.put(u2.getIdUsuario(), u2);
    }

    public static Collection<User> listar() { return usuarios.values(); }
    public static Optional<User> obtener(String id) { return Optional.ofNullable(usuarios.get(id)); }
    public static Optional<User> obtenerPorCorreo(String correo) {
        return usuarios.values().stream().filter(u -> u.getCorreo()!=null && u.getCorreo().equalsIgnoreCase(correo)).findFirst();
    }
    public static void guardar(User user) { usuarios.put(user.getIdUsuario(), user); }
    public static void eliminar(String id) { usuarios.remove(id); }
}
