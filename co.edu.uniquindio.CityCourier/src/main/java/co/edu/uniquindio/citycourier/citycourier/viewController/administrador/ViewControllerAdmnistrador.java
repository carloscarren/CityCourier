package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.controller.AdministradorController;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewControllerAdmnistrador {

    private final AdministradorController administradorController = new AdministradorController();

    @FXML
    private TextArea txtSalida;
    @FXML private TextField txtIdEnvio;
    @FXML private TextField txtNuevoEstado;
    @FXML private TextField txtIdRepartidor;
    @FXML
    private javafx.scene.control.Button btnCerrarSesion;

    @FXML
    protected void onListarEnvios() {
        var lista = administradorController.listarEnvios();
        txtSalida.clear();
        if (lista.isEmpty()) {
            txtSalida.appendText("No hay envíos registrados.\n");
            return;
        }
        txtSalida.appendText("📦 Lista de envíos:\n");
        for (EnvioDto e : lista) {
            txtSalida.appendText("- ID: " + e.idEnvio() + " | Costo: $" + (long)e.costo() + "\n");
        }
    }

    @FXML
    protected void onActualizarEstado() {
        String idEnvio = txtIdEnvio.getText();
        String estado = txtNuevoEstado.getText();
        if (administradorController.actualizarEstadoEnvio(idEnvio, estado)) {
            txtSalida.appendText("✅ Estado actualizado correctamente.\n");
        } else {
            txtSalida.appendText("❌ Error al actualizar estado.\n");
        }
    }

    @FXML
    protected void onAsignarRepartidor() {
        String idEnvio = txtIdEnvio.getText();
        String idRepartidor = txtIdRepartidor.getText();
        if (administradorController.asignarRepartidor(idEnvio, idRepartidor)) {
            txtSalida.appendText("🚚 Repartidor asignado correctamente.\n");
        } else {
            txtSalida.appendText("⚠️ No se pudo asignar el repartidor.\n");
        }
    }

    @FXML
    protected void onListarRepartidores() {
        var lista = administradorController.listarRepartidores();
        txtSalida.appendText("👷‍♂️ Repartidores disponibles:\n");
        for (RepartidorDto r : lista) {
            txtSalida.appendText("- " + r.nombre() + " (Vehículo: " + r.vehiculoAsignado() + ")\n");
        }
    }

    @FXML
    protected void onCerrarSesion() {
        try {
            // Obtener el Stage desde cualquier componente FXML disponible
            Stage stage = null;
            if (btnCerrarSesion != null && btnCerrarSesion.getScene() != null) {
                stage = (Stage) btnCerrarSesion.getScene().getWindow();
            } else if (txtSalida != null && txtSalida.getScene() != null) {
                stage = (Stage) txtSalida.getScene().getWindow();
            } else {
                // Si no hay componentes disponibles, buscar el Stage de otra forma
                javafx.scene.Node node = btnCerrarSesion != null ? btnCerrarSesion : txtSalida;
                if (node != null && node.getScene() != null) {
                    stage = (Stage) node.getScene().getWindow();
                }
            }
            
            if (stage == null) {
                mostrarAlerta("Error", "No se pudo obtener la ventana actual.");
                return;
            }
            
            // Cargar la vista de login
            java.net.URL url = getClass().getResource(
                    "/co/edu/uniquindio/citycourier/citycourier/Login.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró el archivo Login.fxml");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login - CityCourier");
            stage.centerOnScreen();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de login: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}