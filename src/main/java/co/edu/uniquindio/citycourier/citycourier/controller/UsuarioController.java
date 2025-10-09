package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.User;

import java.util.Collection;
import java.util.Optional;

public class UsuarioController {
    public Collection<User> listar() {
        return DataStore.getInstance().getUsuarios().values();
    }

    public Optional<User> obtener(String id) {
        return Optional.ofNullable(DataStore.getInstance().getUsuarios().get(id));
    }

    public void actualizar(User user) {
        DataStore.getInstance().getUsuarios().put(user.getIdUsuario(), user);
    }

    public void eliminar(String id) {
        DataStore.getInstance().getUsuarios().remove(id);
    }
}
