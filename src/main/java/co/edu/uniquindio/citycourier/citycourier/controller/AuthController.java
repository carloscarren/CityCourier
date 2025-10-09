package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.User;

import java.util.Optional;
import java.util.Random;

public class AuthController {
    public Optional<User> login(String email) {
        if (email == null || email.isBlank()) return Optional.empty();
        return DataStore.getInstance().getUsuarios().values().stream()
                .filter(u -> u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(email))
                .findFirst();
    }

    public User register(String nombre, String email, String telefono) {
        String id = "U" + (100 + new Random().nextInt(900));
        User u = new User(id, nombre, email, telefono);
        DataStore.getInstance().getUsuarios().put(id, u);
        DataStore.getInstance().setCurrentUserId(id);
        return u;
    }
}
