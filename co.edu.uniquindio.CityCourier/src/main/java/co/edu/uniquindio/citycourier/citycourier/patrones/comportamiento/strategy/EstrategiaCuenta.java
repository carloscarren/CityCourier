package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy;

import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;

import java.util.List;

public interface EstrategiaCuenta {
    String getTitulo();
    List<String[]> generarContenido(Cuenta cuenta);
}
