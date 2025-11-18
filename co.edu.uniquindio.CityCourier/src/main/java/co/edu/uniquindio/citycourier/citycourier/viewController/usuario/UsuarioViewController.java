package co.edu.uniquindio.citycourier.citycourier.viewController.usuario;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioViewController {

    private final co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController usuarioController = new co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController();

    @FXML
    private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtDistancia;
    @FXML private CheckBox chkPrioridad;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtIdCancelar;
    @FXML private TextArea txtSalida;
    @FXML
    private javafx.scene.control.Button btnCerrarSesion;

    @FXML
    protected void onCotizar() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            double costo = usuarioController.cotizar(peso, volumen, distancia, prioridad);
            println("Costo estimado: $" + (long) costo);
        } catch (Exception e) {
            println("Error al cotizar: " + e.getMessage());
        }
    }

    @FXML
    protected void onCrearEnvio() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            String desc = txtDescripcion.getText();

            Direccion origen = new Direccion("Calle 1 #2-3, Centro", "Armenia", "Frente al parque");
            Direccion destino = new Direccion("Carrera 10 #20-30, Norte", "Armenia", "Edificio azul");

            EnvioDto creado = usuarioController.crearEnvio("U1", origen, destino, desc, peso, volumen, distancia, prioridad);
            println("Envío creado: id=" + creado.idEnvio() + ", costo=$" + (long) creado.costo());
        } catch (Exception e) {
            println("Error al crear envío: " + e.getMessage());
        }
    }

    @FXML
    protected void onListar() {
        if (txtSalida != null) {
            txtSalida.clear();
        }
        var lista = usuarioController.listarEnvios();
        if (lista.isEmpty()) {
            println("No hay envíos registrados.");
            return;
        }
        println("📦 Lista de Envíos:");
        println("===================");
        for (EnvioDto e : lista) {
            println("ID: " + e.idEnvio() + 
                   " | Estado: " + e.estado() + 
                   " | Costo: $" + (long) e.costo() +
                   " | Usuario: " + e.idUsuario());
        }
        println("===================");
        println("Total: " + lista.size() + " envío(s)");
    }

    @FXML
    protected void onCancelar() {
        String id = txtIdCancelar.getText();
        if (id == null || id.isBlank()) {
            println("Ingrese el ID del envío a cancelar.");
            return;
        }
        boolean ok = usuarioController.cancelarEnvio(id);
        println(ok ? "Envío cancelado." : "No se pudo cancelar (no existe o ya está asignado/en ruta).");
    }

    private void println(String s) {
        txtSalida.appendText(s + "\n");
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