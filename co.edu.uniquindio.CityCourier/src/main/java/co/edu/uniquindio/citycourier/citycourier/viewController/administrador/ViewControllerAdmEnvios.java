package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewControllerAdmEnvios {

    @FXML private TableView<EnvioDto> tablaEnvios;
    @FXML private TableColumn<EnvioDto, String> colId;
    @FXML private TableColumn<EnvioDto, String> colEstado;
    @FXML private TableColumn<EnvioDto, String> colRepartidor;
    @FXML private TableColumn<EnvioDto, String> colOrigen;
    @FXML private TableColumn<EnvioDto, String> colDestino;
    @FXML private TableColumn<EnvioDto, Number> colCosto;

    @FXML private TextField txtIdEnvio;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TextField txtIdRepartidor;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar estados disponibles
        cmbEstado.setItems(FXCollections.observableArrayList(
                "SOLICITANDO", "ASIGNADO", "EN_RUTA", "ENTREGADO", "CANCELADO"
        ));

        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().idEnvio())
        );

        colEstado.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado().name())
        );

        colRepartidor.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty("Sin asignar") // Por ahora, se puede mejorar después
        );

        colOrigen.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().direccionOrigen())
        );

        colDestino.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().direccionDestino())
        );

        colCosto.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().costo())
        );

        tablaEnvios.setItems(listaEnvios);
        
        // Listener para selección de tabla
        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtIdEnvio.setText(newVal.idEnvio());
                cmbEstado.setValue(newVal.getEstado().name());
            }
        });
        
        cargarEnvios();
    }

    private void cargarEnvios() {
        listaEnvios.setAll(model.listarEnvios());
    }

    // =============================================================
    // ACTUALIZAR ESTADO
    // =============================================================
    @FXML
    private void onActualizarEstado() {
        String idEnvio = txtIdEnvio.getText();
        String nuevoEstado = cmbEstado.getValue();

        if (idEnvio.isBlank() || nuevoEstado == null) {
            mostrar("Debe seleccionar un envío de la tabla y elegir un estado.");
            return;
        }

        boolean actualizado = model.actualizarEstadoEnvio(idEnvio, nuevoEstado);
        if (actualizado) {
            mostrar("Estado del envío actualizado correctamente.");
            cargarEnvios();
        } else {
            mostrar("No se pudo actualizar el estado (verifique el ID o el estado).");
        }
    }

    @FXML
    private void onEliminarEnvio() {
        EnvioDto seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrar("Seleccione un envío de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar el envío?");
        confirmacion.setContentText("ID: " + seleccionado.idEnvio());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // En un sistema real, esto se haría en el modelo
                listaEnvios.remove(seleccionado);
                mostrar("Envío eliminado correctamente.");
            }
        });
    }

    // =============================================================
    // ASIGNAR REPARTIDOR
    // =============================================================
    @FXML
    private void onAsignarRepartidor() {
        String idEnvio = txtIdEnvio.getText();
        String idRepartidor = txtIdRepartidor.getText();

        if (idEnvio.isBlank() || idRepartidor.isBlank()) {
            mostrar("Debe ingresar el ID del envío y del repartidor.");
            return;
        }

        boolean asignado = model.asignarRepartidorEnvio(idEnvio, idRepartidor);
        if (asignado) {
            mostrar("Repartidor asignado correctamente.");
            cargarEnvios();
        } else {
            mostrar("No se pudo asignar el repartidor (ID inválido o envío ya finalizado).");
        }
    }

    // =============================================================
    // CANCELAR ENVÍO
    // =============================================================
    @FXML
    private void onCancelarEnvio() {
        String id = txtIdEnvio.getText();
        if (id.isBlank()) {
            mostrar("Debe ingresar el ID del envío.");
            return;
        }

        boolean cancelado = model.cancelarEnvio(id);
        if (cancelado) {
            mostrar("Envío cancelado correctamente.");
            cargarEnvios();
        } else {
            mostrar("No se pudo cancelar el envío.");
        }
    }

    // =============================================================
    private void mostrar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
