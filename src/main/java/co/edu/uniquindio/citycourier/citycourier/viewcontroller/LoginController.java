package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import co.edu.uniquindio.citycourier.citycourier.domain.User;
import co.edu.uniquindio.citycourier.citycourier.controller.AuthController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import co.edu.uniquindio.citycourier.citycourier.HelloApplication;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField telefonoField;
    @FXML
    private Label messageLabel;

    @FXML
    public void onLogin(ActionEvent event) {
        String email = emailField.getText();
        if (email == null || email.isBlank()) {
            messageLabel.setText("Ingresa el correo");
            return;
        }
        Optional<User> userOpt = new AuthController().login(email);
        if (userOpt.isPresent()) goToHome(); else messageLabel.setText("No existe, regístrate");
    }

    @FXML
    public void onRegister(ActionEvent event) {
        String email = emailField.getText();
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        if (email == null || email.isBlank() || nombre == null || nombre.isBlank()) {
            messageLabel.setText("Correo y nombre son obligatorios");
            return;
        }
        boolean exists = co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController.obtenerPorCorreo(email).isPresent();
        if (exists) {
            messageLabel.setText("Ya existe un usuario con ese correo");
            return;
        }
        new AuthController().register(nombre, email, telefono);
        messageLabel.setText("Registro exitoso");
        goToHome();
    }

    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("CityCourier");
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Error cargando la pantalla principal");
        }
    }
}
