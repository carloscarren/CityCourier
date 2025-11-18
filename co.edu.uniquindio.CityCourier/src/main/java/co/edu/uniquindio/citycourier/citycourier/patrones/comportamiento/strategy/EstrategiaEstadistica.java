package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.Usuario;

import java.util.List;

public interface EstrategiaEstadistica {
    String getTitulo();
    List<EstadisticaCategoria> calcular(List<Usuario> usuarios);
}
