package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.*;
import co.edu.uniquindio.citycourier.citycourier.controller.EnvioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import co.edu.uniquindio.citycourier.citycourier.HelloApplication;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Random;

public class CotizadorController {
    @FXML
    private ComboBox<Address> origenCombo;
    @FXML
    private ComboBox<Address> destinoCombo;
    @FXML
    private TextField pesoField;
    @FXML
    private TextField volumenField;
    @FXML
    private ChoiceBox<Priority> prioridadChoice;
    @FXML
    private CheckBox seguroCheck;
    @FXML
    private CheckBox fragilCheck;
    @FXML
    private CheckBox firmaCheck;
    @FXML
    private Label resultadoLabel;

    private final EnvioService envioService = new EnvioService();
    private double ultimoCosto;

    @FXML
    private void initialize() {
        DataStore ds = DataStore.getInstance();
        User user = ds.getUsuarios().get(ds.getCurrentUserId());
        if (user != null) {
            origenCombo.setItems(FXCollections.observableArrayList(user.getDireccionesFrecuentes()));
            destinoCombo.setItems(FXCollections.observableArrayList(user.getDireccionesFrecuentes()));
        }
        prioridadChoice.setItems(FXCollections.observableArrayList(Priority.values()));
        prioridadChoice.setValue(Priority.NORMAL);
    }

    @FXML
    private void onCalcular() {
        try {
            Address origen = origenCombo.getValue();
            Address destino = destinoCombo.getValue();
            double peso = Double.parseDouble(pesoField.getText());
            double volumen = Double.parseDouble(volumenField.getText());
            Priority prioridad = prioridadChoice.getValue();
            EnumSet<AdditionalService> servicios = EnumSet.noneOf(AdditionalService.class);
            if (seguroCheck.isSelected()) servicios.add(AdditionalService.SEGURO);
            if (fragilCheck.isSelected()) servicios.add(AdditionalService.FRAGIL);
            if (firmaCheck.isSelected()) servicios.add(AdditionalService.FIRMA_REQUERIDA);

            if (origen == null || destino == null) {
                resultadoLabel.setText("Selecciona origen y destino");
                return;
            }
            double costo = envioService.cotizar(peso, volumen, prioridad, servicios);
            ultimoCosto = costo;
            resultadoLabel.setText("Costo estimado: $" + (long) costo);
        } catch (NumberFormatException e) {
            resultadoLabel.setText("Peso/volumen inválidos");
        }
    }

    @FXML
    private void onCrearEnvio() {
        Address origen = origenCombo.getValue();
        Address destino = destinoCombo.getValue();
        if (origen == null || destino == null) {
            resultadoLabel.setText("Selecciona origen y destino");
            return;
        }
        try {
            double peso = Double.parseDouble(pesoField.getText());
            double volumen = Double.parseDouble(volumenField.getText());
            Priority prioridad = prioridadChoice.getValue();
            EnumSet<AdditionalService> servicios = EnumSet.noneOf(AdditionalService.class);
            if (seguroCheck.isSelected()) servicios.add(AdditionalService.SEGURO);
            if (fragilCheck.isSelected()) servicios.add(AdditionalService.FRAGIL);
            if (firmaCheck.isSelected()) servicios.add(AdditionalService.FIRMA_REQUERIDA);

            Shipment s = envioService.crearEnvio(origen, destino, peso, volumen, prioridad, servicios);
            resultadoLabel.setText("Envío creado: " + s.getIdEnvio() + " por $" + (long) s.getCosto());
        } catch (NumberFormatException e) {
            resultadoLabel.setText("Peso/volumen inválidos");
        }
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            Stage stage = (Stage) resultadoLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            resultadoLabel.setText("Error al volver");
        }
    }
}
