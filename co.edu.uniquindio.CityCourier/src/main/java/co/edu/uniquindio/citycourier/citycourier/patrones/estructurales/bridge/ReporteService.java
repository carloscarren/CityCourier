package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;

import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.strategy.*;

import java.util.List;

public class ReporteService {
    public void generarReporteUsuariosActivos(List<Usuario> usuarios, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaUsuariosConMasTransacciones();
        Reporte reporte = new ReporteEstadisticaGenerica(estrategia, exportador, usuarios);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReporteSaldoPromedio(List<Usuario> usuarios, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaSaldoPromedio();
        Reporte reporte = new ReporteEstadisticaGenerica(estrategia, exportador, usuarios);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReporteGastosMasComunes(List<Usuario> usuarios, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaGastosPorCategoria();
        Reporte reporte = new ReporteEstadisticaGenerica(estrategia, exportador, usuarios);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReporteGastosUsuario(Usuario usuario, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaReporteGastos();
        Reporte reporte = new ReporteUsuarioIndividual(usuario, estrategia, exportador);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReporteIngresosUsuario(Usuario usuario, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaReporteIngresos();
        Reporte reporte = new ReporteUsuarioIndividual(usuario, estrategia, exportador);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReporteSaldosUsuario(Usuario usuario, String nombreArchivo, Exportador exportador) {
        EstrategiaEstadistica estrategia = new EstrategiaSaldosPorCuenta();
        Reporte reporte = new ReporteUsuarioIndividual(usuario, estrategia, exportador);
        reporte.generarYExportar(nombreArchivo);
    }

    public void generarReportePorCuenta(Cuenta cuenta, String nombreArchivo, Exportador exportador) {
        EstrategiaCuenta estrategia = new EstrategiaReporteCuentaDetallada();
        List<String[]> contenido = estrategia.generarContenido(cuenta);
        exportador.exportar(estrategia.getTitulo(), contenido, nombreArchivo);
    }

}
