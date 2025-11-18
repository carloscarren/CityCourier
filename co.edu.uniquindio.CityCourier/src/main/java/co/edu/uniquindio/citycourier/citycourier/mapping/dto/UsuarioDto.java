package co.edu.uniquindio.citycourier.citycourier.mapping.dto;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;

import java.util.List;

public record UsuarioDto(
        String idUsuario,
        String nombre,
        String correo,
        String telefono,
        tipoUsuario tipo,
        List<String> direcciones
) {}
