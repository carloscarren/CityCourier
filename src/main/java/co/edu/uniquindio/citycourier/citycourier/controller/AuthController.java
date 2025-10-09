package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.User;

import java.util.Optional;
import java.util.Random;

public class AuthController {
    private static String currentUserId;

    public Optional<User> login(String email) {
        if (email == null || email.isBlank()) return Optional.empty();
        return UsuarioController.obtenerPorCorreo(email).map(u -> {
            currentUserId = u.getIdUsuario();
            return u;
        });
    }

    public User register(String nombre, String email, String telefono) {
        String id = "U" + (100 + new Random().nextInt(900));
        User u = new User(id, nombre, email, telefono);
        UsuarioController.guardar(u);
        currentUserId = id;
        return u;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }
}
