package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;

public abstract class Reporte {
    protected Exportador exportador;
    public Reporte(Exportador exportador) {
        this.exportador = exportador;
    }

    public abstract void generarYExportar(String nombreArchivo);
}
