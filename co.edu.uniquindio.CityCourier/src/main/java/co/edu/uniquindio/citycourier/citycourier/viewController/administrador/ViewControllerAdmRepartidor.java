package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewControllerAdmRepartidor {

    @FXML private TableView<RepartidorDto> tablaRepartidores;
    @FXML private TableColumn<RepartidorDto, String> colId;
    @FXML private TableColumn<RepartidorDto, String> colNombre;
    @FXML private TableColumn<RepartidorDto, String> colTelefono;
    @FXML private TableColumn<RepartidorDto, String> colVehiculo;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtVehiculo;
    @FXML private TextField txtZonaCobertura;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().idRepartidor()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().nombre()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().telefono()));
        colVehiculo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().vehiculoAsignado()));

        tablaRepartidores.setItems(listaRepartidores);
        cargarRepartidores();
    }

    private void cargarRepartidores() {
        listaRepartidores.setAll(model.listarRepartidores());
    }

    @FXML
    private void onAgregarRepartidor() {
        try {
            RepartidorDto nuevo = new RepartidorDto(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtVehiculo.getText(),
                    txtZonaCobertura.getText()
            );
            boolean creado = model.crearRepartidor(nuevo);
            if (creado) {
                cargarRepartidores();
                limpiarCampos();
                mostrarMensaje("Repartidor agregado correctamente.");
            } else {
                mostrarMensaje("No se pudo crear el repartidor (ya existe o datos inválidos).");
            }
        } catch (Exception e) {
            mostrarMensaje("Error al agregar repartidor: " + e.getMessage());
        }
    }

    @FXML
    private void onEliminarRepartidor() {
        String id = txtId.getText();
        if (id.isBlank()) {
            mostrarMensaje("Ingrese el ID del repartidor a eliminar.");
            return;
        }

        // ⚠️ Esta función aún no está implementada en ModelCityCourier
        // La agregaremos después (método eliminarRepartidor).
        mostrarMensaje("Función eliminar repartidor próximamente implementada.");
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtVehiculo.clear();
        txtZonaCobertura.clear();
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
