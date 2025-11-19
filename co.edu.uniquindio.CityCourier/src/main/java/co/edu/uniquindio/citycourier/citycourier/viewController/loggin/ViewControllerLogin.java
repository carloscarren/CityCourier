package co.edu.uniquindio.citycourier.citycourier.viewController.loggin;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewControllerLogin {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    private final ModelCityCourier model = ModelCityCourier.getInstance();

    @FXML
    private void onLogin() {
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();

        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            mostrarAlerta("Error", "Por favor ingrese correo y contraseña");
            return;
        }

        // Buscar usuario por correo y validar contraseña
        UsuarioDto usuarioEncontrado = model.autenticarUsuario(correo.trim(), contrasena);

        if (usuarioEncontrado == null) {
            mostrarAlerta("Error", "Correo o contraseña incorrectos.\n\nCredenciales de prueba:\n" +
                    "Admin: admin@citycourier.com / admin123\n" +
                    "Usuario: carlos@mail.com / 1234");
            return;
        }
        
        // Verificar que el tipo de usuario esté correctamente asignado
        if (usuarioEncontrado.tipo() == null) {
            mostrarAlerta("Error", "Error: El usuario no tiene un tipo asignado.");
            return;
        }

        // Navegación según tipo de usuario
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            if (usuarioEncontrado.tipo() == tipoUsuario.ADMINISTRADOR) {
                java.net.URL url = getClass().getResource(
                        "/co/edu/uniquindio/citycourier/citycourier/AdministradorView.fxml");
                if (url == null) {
                    mostrarAlerta("Error", "No se encontró el archivo AdministradorView.fxml");
                    return;
                }
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                Scene scene = new Scene(root, width, height);
                stage.setScene(scene);
                stage.setTitle("Panel de Administración - CityCourier");
            } else {
                java.net.URL url = getClass().getResource(
                        "/co/edu/uniquindio/citycourier/citycourier/VistaUsuario.fxml");
                if (url == null) {
                    mostrarAlerta("Error", "No se encontró el archivo VistaUsuario.fxml");
                    return;
                }
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                
                // Pasar el usuario al controlador
                co.edu.uniquindio.citycourier.citycourier.viewController.usuario.UsuarioViewController controller = 
                    loader.getController();
                if (controller != null) {
                    controller.inicializarUsuario(usuarioEncontrado);
                }
                
                Scene scene = new Scene(root, width, height);
                stage.setScene(scene);
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegistrar() {
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();

            java.net.URL url = getClass().getResource(
                    "/co/edu/uniquindio/citycourier/citycourier/Registro.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró el archivo Registro.fxml", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("CityCourier - Registro");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de registro: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error inesperado: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        mostrarAlerta(titulo, mensaje, Alert.AlertType.ERROR);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
