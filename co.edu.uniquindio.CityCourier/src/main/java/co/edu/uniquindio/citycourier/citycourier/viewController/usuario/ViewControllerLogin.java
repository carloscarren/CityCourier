package co.edu.uniquindio.citycourier.citycourier.viewController.usuario;

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
        UsuarioDto usuarioEncontrado = model.autenticarUsuario(correo, contrasena);

        if (usuarioEncontrado == null) {
            mostrarAlerta("Error", "Correo o contraseña incorrectos");
            return;
        }

        // Navegación según tipo de usuario
        try {
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            
            if (usuarioEncontrado.tipo() == tipoUsuario.ADMINISTRADOR) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/co/edu/uniquindio/citycourier/citycourier/AdministradorView.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/co/edu/uniquindio/citycourier/citycourier/VistaUsuario.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegistrar() {
        // TODO: Implementar funcionalidad de registro
        mostrarAlerta("Información", "Funcionalidad de registro próximamente disponible", Alert.AlertType.INFORMATION);
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
