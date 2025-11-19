package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ExportadorPDF;
import co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ReporteService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Controlador para la pestaña de Métricas del Administrador.
 * Permite exportar reportes estadísticos del sistema.
 * 
 * Responsabilidad Única: Gestionar la exportación de reportes administrativos.
 */
public class ViewControllerMetricas {

    // ==================== COMPONENTES FXML ====================
    
    @FXML private Button btnExportarUsuariosActivos;
    @FXML private Button btnExportarSaldoPromedio;
    
    // ==================== DEPENDENCIAS ====================
    
    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ReporteService reporteService = new ReporteService();
    
    // ==================== INICIALIZACIÓN ====================
    
    @FXML
    public void initialize() {
        // Inicialización básica - no requiere configuración adicional
    }
    
    // ==================== MÉTODOS DE EXPORTACIÓN ====================
    
    /**
     * Exporta un reporte de usuarios activos en formato PDF.
     * Permite al administrador seleccionar dónde guardar el archivo.
     */
    @FXML
    private void onExportarUsuariosActivos() {
        try {
            // Obtener lista de usuarios como entidades
            List<Usuario> usuarios = model.obtenerUsuariosEntidad();
            
            if (usuarios == null || usuarios.isEmpty()) {
                mostrarMensaje("Advertencia", 
                    "No hay usuarios registrados en el sistema.", 
                    Alert.AlertType.WARNING);
                return;
            }

            // Abrir FileChooser para seleccionar dónde guardar el PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte de Usuarios Activos");
            fileChooser.setInitialFileName("reporte_usuarios_activos");
            
            // Configurar filtro para archivos PDF
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Obtener el Stage
            Stage stage = obtenerStage();
            if (stage == null) {
                mostrarMensaje("Error", 
                    "No se pudo obtener la ventana actual.", 
                    Alert.AlertType.ERROR);
                return;
            }

            File archivoSeleccionado = fileChooser.showSaveDialog(stage);
            
            if (archivoSeleccionado == null) {
                // Usuario canceló la operación
                return;
            }

            // Obtener la ruta del archivo (sin extensión .pdf)
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();
            if (rutaArchivo.endsWith(".pdf")) {
                rutaArchivo = rutaArchivo.substring(0, rutaArchivo.length() - 4);
            }

            // Instanciar ExportadorPDF
            ExportadorPDF exportadorPDF = new ExportadorPDF();

            // Generar el reporte
            reporteService.generarReporteUsuariosActivos(usuarios, rutaArchivo, exportadorPDF);

            mostrarMensaje("Éxito", 
                "Reporte de usuarios activos generado correctamente en:\n" + rutaArchivo + ".pdf", 
                Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarMensaje("Error", 
                "Error al generar el reporte: " + e.getMessage(), 
                Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Exporta un reporte de saldo promedio en formato PDF.
     * Permite al administrador seleccionar dónde guardar el archivo.
     */
    @FXML
    private void onExportarSaldoPromedio() {
        try {
            // Obtener lista de usuarios como entidades
            List<Usuario> usuarios = model.obtenerUsuariosEntidad();
            
            if (usuarios == null || usuarios.isEmpty()) {
                mostrarMensaje("Advertencia", 
                    "No hay usuarios registrados en el sistema.", 
                    Alert.AlertType.WARNING);
                return;
            }

            // Abrir FileChooser para seleccionar dónde guardar el PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte de Saldo Promedio");
            fileChooser.setInitialFileName("reporte_saldo_promedio");
            
            // Configurar filtro para archivos PDF
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Obtener el Stage
            Stage stage = obtenerStage();
            if (stage == null) {
                mostrarMensaje("Error", 
                    "No se pudo obtener la ventana actual.", 
                    Alert.AlertType.ERROR);
                return;
            }

            File archivoSeleccionado = fileChooser.showSaveDialog(stage);
            
            if (archivoSeleccionado == null) {
                // Usuario canceló la operación
                return;
            }

            // Obtener la ruta del archivo (sin extensión .pdf)
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();
            if (rutaArchivo.endsWith(".pdf")) {
                rutaArchivo = rutaArchivo.substring(0, rutaArchivo.length() - 4);
            }

            // Instanciar ExportadorPDF
            ExportadorPDF exportadorPDF = new ExportadorPDF();

            // Generar el reporte
            reporteService.generarReporteSaldoPromedio(usuarios, rutaArchivo, exportadorPDF);

            mostrarMensaje("Éxito", 
                "Reporte de saldo promedio generado correctamente en:\n" + rutaArchivo + ".pdf", 
                Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarMensaje("Error", 
                "Error al generar el reporte: " + e.getMessage(), 
                Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Obtiene el Stage actual desde cualquier componente disponible.
     * @return Stage si se encuentra, null en caso contrario
     */
    private Stage obtenerStage() {
        if (btnExportarUsuariosActivos != null && btnExportarUsuariosActivos.getScene() != null) {
            return (Stage) btnExportarUsuariosActivos.getScene().getWindow();
        }
        if (btnExportarSaldoPromedio != null && btnExportarSaldoPromedio.getScene() != null) {
            return (Stage) btnExportarSaldoPromedio.getScene().getWindow();
        }
        return null;
    }
    
    /**
     * Muestra un mensaje al usuario.
     * @param titulo Título del mensaje
     * @param mensaje Contenido del mensaje
     * @param tipo Tipo de alerta
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo, mensaje, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

