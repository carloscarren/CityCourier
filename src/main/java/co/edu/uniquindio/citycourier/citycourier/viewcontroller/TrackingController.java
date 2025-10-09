package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import co.edu.uniquindio.citycourier.citycourier.domain.Shipment;
import co.edu.uniquindio.citycourier.citycourier.domain.ShippingStatus;
import co.edu.uniquindio.citycourier.citycourier.controller.AuthController;
import co.edu.uniquindio.citycourier.citycourier.controller.EnvioController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import co.edu.uniquindio.citycourier.citycourier.HelloApplication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrackingController {
    @FXML
    private ChoiceBox<ShippingStatus> estadoChoice;
    @FXML
    private TableView<Shipment> table;
    @FXML
    private TableColumn<Shipment, String> idCol;
    @FXML
    private TableColumn<Shipment, String> origenCol;
    @FXML
    private TableColumn<Shipment, String> destinoCol;
    @FXML
    private TableColumn<Shipment, String> estadoCol;
    @FXML
    private TableColumn<Shipment, String> costoCol;

    private ObservableList<Shipment> data;

    @FXML
    private void initialize() {
        estadoChoice.setItems(FXCollections.observableArrayList(ShippingStatus.values()));
        idCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdEnvio()));
        origenCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigen().toString()));
        destinoCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestino().toString()));
        estadoCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().name()));
        costoCol.setCellValueFactory(c -> new SimpleStringProperty("$" + (long) c.getValue().getCosto()));
        cargar();
    }

    private void cargar() {
        String uid = AuthController.getCurrentUserId();
        data = FXCollections.observableArrayList(EnvioController.listarPorUsuario(uid));
        table.setItems(data);
    }

    @FXML
    private void onFiltrar() {
        ShippingStatus selected = estadoChoice.getValue();
        if (selected == null) {
            cargar();
        } else {
            String uid = AuthController.getCurrentUserId();
            List<Shipment> filtered = EnvioController.listarPorUsuario(uid).stream()
                    .filter(s -> s.getEstado() == selected)
                    .collect(Collectors.toList());
            data.setAll(filtered);
        }
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // ignore simple
        }
    }
}
