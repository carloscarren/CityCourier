package co.edu.uniquindio.citycourier.citycourier.viewcontroller;

import co.edu.uniquindio.citycourier.citycourier.domain.Address;
import co.edu.uniquindio.citycourier.citycourier.domain.User;
import co.edu.uniquindio.citycourier.citycourier.controller.AuthController;
import co.edu.uniquindio.citycourier.citycourier.controller.DireccionController;
import co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import co.edu.uniquindio.citycourier.citycourier.HelloApplication;

import java.io.IOException;
import java.util.Random;

public class AddressesController {
    @FXML
    private TableView<Address> table;
    @FXML
    private TableColumn<Address, String> aliasCol;
    @FXML
    private TableColumn<Address, String> calleCol;
    @FXML
    private TableColumn<Address, String> ciudadCol;

    @FXML
    private TextField aliasField;
    @FXML
    private TextField calleField;
    @FXML
    private TextField ciudadField;

    private ObservableList<Address> data;

    @FXML
    private void initialize() {
        aliasCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAlias()));
        calleCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCalle()));
        ciudadCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCiudad()));
        loadData();
    }

    private void loadData() {
        String uid = AuthController.getCurrentUserId();
        User user = UsuarioController.obtener(uid).orElse(null);
        if (user == null) {
            data = FXCollections.observableArrayList();
        } else {
            data = FXCollections.observableArrayList(user.getDireccionesFrecuentes());
        }
        table.setItems(data);
    }

    @FXML
    private void onAdd() {
        if (aliasField.getText().isBlank() || calleField.getText().isBlank() || ciudadField.getText().isBlank()) {
            return;
        }
        String id = "A" + (100 + new Random().nextInt(900));
        Address a = new Address(id, aliasField.getText(), calleField.getText(), ciudadField.getText(), 0, 0);
        String uid = AuthController.getCurrentUserId();
        User user = UsuarioController.obtener(uid).orElse(null);
        if (user != null) {
            new DireccionController().crear(uid, a.getAlias(), a.getCalle(), a.getCiudad());
            data.add(a);
            aliasField.clear();
            calleField.clear();
            ciudadField.clear();
        }
    }

    @FXML
    private void onDelete() {
        Address selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        String uid = AuthController.getCurrentUserId();
        User user = UsuarioController.obtener(uid).orElse(null);
        if (user != null) {
            new DireccionController().eliminar(uid, selected.getIdDireccion());
            data.remove(selected);
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
            // simple ignore for now
        }
    }
}
