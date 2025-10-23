package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.PaqueteDto;
import co.edu.uniquindio.citycourier.citycourier.model.Paquete;

import java.util.ArrayList;
import java.util.List;

public class PaqueteMapper {

    public static PaqueteDto paqueteToPaqueteDto(Paquete paquete) {
        if (paquete == null) return null;

        return new PaqueteDto(
                paquete.getIdPaquete(),
                paquete.getPeso(),
                paquete.getVolumen(),
                paquete.getDescripcion()
        );
    }

    public static Paquete paqueteDtoToPaquete(PaqueteDto paqueteDto) {
        if (paqueteDto == null) return null;

        Paquete paquete = new Paquete();
        paquete.setIdPaquete(paqueteDto.idPaquete());
        paquete.setPeso(paqueteDto.peso());
        paquete.setVolumen(paqueteDto.volumen());
        paquete.setDescripcion(paqueteDto.descripcion());
        return paquete;
    }
}
