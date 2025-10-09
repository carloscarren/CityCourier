package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import co.edu.uniquindio.citycourier.citycourier.HelloApplication;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("addresses-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mis direcciones");
            stage.show();
        } catch (IOException e) {
            welcomeText.setText("No se pudo abrir direcciones");
        }
    }

    @FXML
    protected void onQuoteClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("cotizador-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 450);
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cotizar envío");
            stage.show();
        } catch (IOException e) {
            welcomeText.setText("No se pudo abrir cotizador");
        }
    }

    @FXML
    protected void onTrackingClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("tracking-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 450);
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mis envíos");
            stage.show();
        } catch (IOException e) {
            welcomeText.setText("No se pudo abrir Mis envíos");
        }
    }

    @FXML
    private void initialize() {
        DataStore ds = DataStore.getInstance();
        String currentUserId = ds.getCurrentUserId();
        if (currentUserId != null) {
            User u = ds.getUsuarios().get(currentUserId);
            if (u != null) {
                welcomeText.setText("Bienvenido, " + u.getNombreCompleto());
            }
        }
    }
}