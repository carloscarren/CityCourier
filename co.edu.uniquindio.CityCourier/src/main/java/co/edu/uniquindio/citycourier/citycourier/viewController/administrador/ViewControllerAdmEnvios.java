package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
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
    // ACTUALIZAR ESTADO DEL ENVÍO
    // Responsabilidad Única: Solo gestiona el ciclo de vida del envío
    // =============================================================
    @FXML
    private void onActualizarEstado() {
        String idEnvio = txtIdEnvio.getText();
        String nuevoEstado = cmbEstado.getValue();

        if (idEnvio.isBlank() || nuevoEstado == null) {
            mostrar("Debe seleccionar un envío de la tabla y elegir un estado.");
            return;
        }

        // Validar transiciones de estado válidas
        EnvioDto envioSeleccionado = listaEnvios.stream()
                .filter(e -> e.idEnvio().equals(idEnvio))
                .findFirst()
                .orElse(null);

        if (envioSeleccionado == null) {
            mostrar("No se encontró el envío seleccionado.");
            return;
        }

        estadoEnvio estadoActual = envioSeleccionado.getEstado();
        estadoEnvio estadoNuevo;
        try {
            estadoNuevo = estadoEnvio.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            mostrar("Estado inválido.");
            return;
        }

        // Validar transición de estado lógica
        if (estadoActual == estadoEnvio.ENTREGADO && estadoNuevo != estadoEnvio.ENTREGADO) {
            mostrar("No se puede cambiar el estado de un envío ya entregado.");
            return;
        }

        if (estadoActual == estadoEnvio.CANCELADO && estadoNuevo != estadoEnvio.CANCELADO) {
            mostrar("No se puede cambiar el estado de un envío cancelado.");
            return;
        }

        boolean actualizado = model.actualizarEstadoEnvio(idEnvio, nuevoEstado);
        if (actualizado) {
            mostrar("Estado del envío actualizado correctamente: " + estadoActual + " → " + estadoNuevo);
            cargarEnvios();
            limpiarCampos();
        } else {
            mostrar("No se pudo actualizar el estado (verifique el ID o el estado).");
        }
    }

    // =============================================================
    // CANCELAR ENVÍO
    // Responsabilidad Única: Solo cancela envíos en estado SOLICITANDO
    // =============================================================
    @FXML
    private void onCancelarEnvio() {
        String id = txtIdEnvio.getText();
        if (id.isBlank()) {
            mostrar("Debe seleccionar un envío de la tabla.");
            return;
        }

        EnvioDto envioSeleccionado = listaEnvios.stream()
                .filter(e -> e.idEnvio().equals(id))
                .findFirst()
                .orElse(null);

        if (envioSeleccionado == null) {
            mostrar("No se encontró el envío seleccionado.");
            return;
        }

        // Solo se pueden cancelar envíos en estado SOLICITANDO
        if (envioSeleccionado.getEstado() != estadoEnvio.SOLICITANDO) {
            mostrar("Solo se pueden cancelar envíos en estado SOLICITANDO. Estado actual: " + envioSeleccionado.getEstado());
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar cancelación");
        confirmacion.setHeaderText("¿Está seguro de cancelar este envío?");
        confirmacion.setContentText("ID: " + envioSeleccionado.idEnvio() + "\nEstado actual: " + envioSeleccionado.getEstado());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean cancelado = model.cancelarEnvio(id);
                if (cancelado) {
                    mostrar("Envío cancelado correctamente.");
                    cargarEnvios();
                    limpiarCampos();
                } else {
                    mostrar("No se pudo cancelar el envío.");
                }
            }
        });
    }

    // =============================================================
    // ELIMINAR ENVÍO
    // Responsabilidad Única: Solo elimina envíos de la lista
    // =============================================================
    @FXML
    private void onEliminarEnvio() {
        EnvioDto seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrar("Seleccione un envío de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar este envío?");
        confirmacion.setContentText("ID: " + seleccionado.idEnvio() + "\nEstado: " + seleccionado.getEstado() + 
                "\n\nEsta acción no se puede deshacer.");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // En un sistema real, esto se haría en el modelo
                listaEnvios.remove(seleccionado);
                mostrar("Envío eliminado correctamente.");
                limpiarCampos();
            }
        });
    }

    // =============================================================
    // MÉTODOS AUXILIARES
    // =============================================================
    private void limpiarCampos() {
        txtIdEnvio.clear();
        cmbEstado.setValue(null);
    }

    private void mostrar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
