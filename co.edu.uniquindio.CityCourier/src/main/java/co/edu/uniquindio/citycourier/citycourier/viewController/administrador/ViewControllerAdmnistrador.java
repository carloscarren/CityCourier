package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.controller.AdministradorController;
import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

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
    private TabPane tabPaneAdmin;
    
    @FXML
    public void initialize() {
        // Agregar listener para refrescar datos cuando se cambia de pestaña
        if (tabPaneAdmin != null) {
            tabPaneAdmin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Platform.runLater(() -> {
                        refrescarDatosPestaña(newValue);
                    });
                }
            });
        }
    }
    
    /**
     * Refresca los datos de la pestaña seleccionada
     * Usa lookup para encontrar los nodos y refrescar los datos
     */
    private void refrescarDatosPestaña(Tab tab) {
        String nombreTab = tab.getText();
        
        // Buscar en la escena completa usando lookup
        if (tabPaneAdmin != null && tabPaneAdmin.getScene() != null) {
            javafx.scene.Node root = tabPaneAdmin.getScene().getRoot();
            if (root != null) {
                switch (nombreTab) {
                    case "Asignar Envío":
                        // Buscar el ComboBox de repartidor y refrescar la lista
                        // El controlador se refrescará automáticamente cuando se acceda a la pestaña
                        // porque carga datos en initialize, pero podemos forzar un refresco
                        // buscando el nodo y verificando que existe
                        javafx.scene.Node cmbRepartidor = root.lookup("#cmbRepartidor");
                        if (cmbRepartidor != null) {
                            // El nodo existe, el controlador debería refrescarse automáticamente
                            // al acceder a la pestaña porque el método refrescarDatos() se llama
                        }
                        break;
                    case "Rutas":
                        // Buscar el ComboBox de ciudades - si existe, refrescar
                        javafx.scene.Node cmbCiudad = root.lookup("#cmbCiudadOrigen");
                        if (cmbCiudad != null && cmbCiudad instanceof javafx.scene.control.ComboBox) {
                            // Recargar ciudades desde el modelo
                            @SuppressWarnings("unchecked")
                            javafx.scene.control.ComboBox<String> comboBox = (javafx.scene.control.ComboBox<String>) cmbCiudad;
                            var ciudades = ModelCityCourier.getInstance().obtenerNombresCiudades();
                            comboBox.getItems().setAll(ciudades);
                            
                            // También refrescar el ComboBox de destino
                            javafx.scene.Node cmbDestino = root.lookup("#cmbCiudadDestino");
                            if (cmbDestino != null && cmbDestino instanceof javafx.scene.control.ComboBox) {
                                @SuppressWarnings("unchecked")
                                javafx.scene.control.ComboBox<String> comboBoxDestino = (javafx.scene.control.ComboBox<String>) cmbDestino;
                                comboBoxDestino.getItems().setAll(ciudades);
                            }
                        }
                        break;
                }
            }
        }
    }

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