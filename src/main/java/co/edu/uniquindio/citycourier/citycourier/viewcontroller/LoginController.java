package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.User;
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
        DataStore ds = DataStore.getInstance();
        Optional<User> userOpt = ds.getUsuarios().values().stream()
                .filter(u -> u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(email))
                .findFirst();
        if (userOpt.isPresent()) {
            ds.setCurrentUserId(userOpt.get().getIdUsuario());
            goToHome();
        } else {
            messageLabel.setText("No existe, regístrate");
        }
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
        DataStore ds = DataStore.getInstance();
        boolean exists = ds.getUsuarios().values().stream()
                .anyMatch(u -> u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(email));
        if (exists) {
            messageLabel.setText("Ya existe un usuario con ese correo");
            return;
        }
        String id = "U" + (100 + new Random().nextInt(900));
        User u = new User(id, nombre, email, telefono);
        ds.getUsuarios().put(id, u);
        ds.setCurrentUserId(id);
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
