package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.User;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.AddressDTO;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UserDTO;

import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}

    public static UserDTO toDTO(User u) {
        if (u == null) return null;
        UserDTO dto = new UserDTO();
        dto.idUsuario = u.getIdUsuario();
        dto.nombreCompleto = u.getNombreCompleto();
        dto.correo = u.getCorreo();
        dto.telefono = u.getTelefono();
        dto.direcciones = u.getDireccionesFrecuentes().stream().map(AddressMapper::toDTO).collect(Collectors.toList());
        return dto;
    }
}
