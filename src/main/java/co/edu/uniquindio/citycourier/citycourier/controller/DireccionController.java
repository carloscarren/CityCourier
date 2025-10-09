package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.Address;
import co.edu.uniquindio.citycourier.citycourier.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DireccionController {
    public List<Address> listarDelUsuario(String idUsuario) {
        User u = DataStore.getInstance().getUsuarios().get(idUsuario);
        return u != null ? u.getDireccionesFrecuentes() : List.of();
    }

    public Address crear(String idUsuario, String alias, String calle, String ciudad) {
        User u = DataStore.getInstance().getUsuarios().get(idUsuario);
        if (u == null) return null;
        Address a = new Address("A" + (100 + new Random().nextInt(900)), alias, calle, ciudad, 0, 0);
        u.getDireccionesFrecuentes().add(a);
        return a;
    }

    public void eliminar(String idUsuario, String idDireccion) {
        User u = DataStore.getInstance().getUsuarios().get(idUsuario);
        if (u != null) {
            u.getDireccionesFrecuentes().removeIf(d -> d.getIdDireccion().equals(idDireccion));
        }
    }

    public Optional<Address> obtener(String idUsuario, String idDireccion) {
        return listarDelUsuario(idUsuario).stream().filter(d -> d.getIdDireccion().equals(idDireccion)).findFirst();
    }
}
