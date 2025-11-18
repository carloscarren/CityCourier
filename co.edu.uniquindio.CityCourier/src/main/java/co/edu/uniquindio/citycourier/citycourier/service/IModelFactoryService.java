package co.edu.uniquindio.citycourier.citycourier.service;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;

import java.util.List;

    public interface IModelFactoryService {
        List<UsuarioDto> obtenerUsuarios();
        List<EnvioDto> obtenerEnvios();
    }