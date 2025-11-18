package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;

import java.util.List;

public interface Exportador {
    void exportar(String titulo, List<String[]> filas, String nombreArchivo); // ✅

}
