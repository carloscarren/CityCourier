package co.edu.uniquindio.citycourier.citycourier;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.Shipment;
import co.edu.uniquindio.citycourier.citycourier.domain.ShippingStatus;
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
        DataStore ds = DataStore.getInstance();
        List<Shipment> all = ds.getEnvios().values().stream()
                .filter(s -> ds.getCurrentUserId() != null && ds.getCurrentUserId().equals(s.getIdUsuario()))
                .collect(Collectors.toList());
        data = FXCollections.observableArrayList(all);
        table.setItems(data);
    }

    @FXML
    private void onFiltrar() {
        ShippingStatus selected = estadoChoice.getValue();
        if (selected == null) {
            cargar();
        } else {
            DataStore ds = DataStore.getInstance();
            List<Shipment> filtered = ds.getEnvios().values().stream()
                    .filter(s -> ds.getCurrentUserId() != null && ds.getCurrentUserId().equals(s.getIdUsuario()))
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
