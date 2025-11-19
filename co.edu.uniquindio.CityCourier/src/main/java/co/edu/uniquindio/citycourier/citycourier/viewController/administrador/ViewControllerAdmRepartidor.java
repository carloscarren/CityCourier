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
    @FXML private TableColumn<RepartidorDto, String> colEstado;
    @FXML private TableColumn<RepartidorDto, String> colVehiculo;
    @FXML private TableColumn<RepartidorDto, String> colZona;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private ComboBox<String> cmbZonaCobertura;
    @FXML private TextField txtVehiculo;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar estados disponibles
        cmbEstado.setItems(FXCollections.observableArrayList("ACTIVO", "INACTIVO", "EN_RUTA"));
        
        // Configurar zonas de cobertura
        cmbZonaCobertura.setItems(FXCollections.observableArrayList("Centro", "Norte", "Sur", "Este", "Oeste"));
        
        // Configurar columnas de la tabla
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().idRepartidor()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().nombre()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().telefono()));
        colEstado.setCellValueFactory(data -> {
            var estado = data.getValue().estado();
            return new javafx.beans.property.SimpleStringProperty(
                    estado != null ? estado.name() : "ACTIVO"
            );
        });
        colVehiculo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().vehiculoAsignado()));
        colZona.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().zonaCobertura()));

        tablaRepartidores.setItems(listaRepartidores);
        
        // Listener para selección de tabla
        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarDatosRepartidor(newVal);
            }
        });
        
        cargarRepartidores();
    }

    private void cargarRepartidores() {
        listaRepartidores.setAll(model.listarRepartidores());
    }

    private void cargarDatosRepartidor(RepartidorDto repartidor) {
        txtId.setText(repartidor.idRepartidor());
        txtNombre.setText(repartidor.nombre());
        txtTelefono.setText(repartidor.telefono());
        txtVehiculo.setText(repartidor.vehiculoAsignado());
        cmbZonaCobertura.setValue(repartidor.zonaCobertura());
        if (repartidor.estado() != null) {
            cmbEstado.setValue(repartidor.estado().name());
        } else {
            cmbEstado.setValue("ACTIVO");
        }
    }

    @FXML
    private void onRegistrarRepartidor() {
        try {
            if (txtId.getText().isBlank() || txtNombre.getText().isBlank() || 
                txtTelefono.getText().isBlank() || cmbZonaCobertura.getValue() == null) {
                mostrarMensaje("Por favor complete todos los campos obligatorios.");
                return;
            }

            // Obtener el estado seleccionado o usar ACTIVO por defecto
            co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor estado = 
                    co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor.ACTIVO;
            if (cmbEstado.getValue() != null) {
                try {
                    estado = co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor.valueOf(
                            cmbEstado.getValue().toUpperCase());
                } catch (IllegalArgumentException e) {
                    estado = co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor.ACTIVO;
                }
            }
            
            RepartidorDto nuevo = new RepartidorDto(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtVehiculo.getText().isBlank() ? "Sin asignar" : txtVehiculo.getText(),
                    cmbZonaCobertura.getValue(),
                    estado
            );
            boolean creado = model.crearRepartidor(nuevo);
            if (creado) {
                cargarRepartidores();
                limpiarCampos();
                mostrarMensaje("Repartidor registrado correctamente.");
            } else {
                mostrarMensaje("No se pudo crear el repartidor (ya existe o datos inválidos).");
            }
        } catch (Exception e) {
            mostrarMensaje("Error al registrar repartidor: " + e.getMessage());
        }
    }

    @FXML
    private void onActualizarRepartidor() {
        String id = txtId.getText();
        if (id.isBlank()) {
            mostrarMensaje("Seleccione un repartidor de la tabla para actualizar.");
            return;
        }

        // Buscar el repartidor en la lista
        RepartidorDto repartidorExistente = listaRepartidores.stream()
                .filter(r -> r.idRepartidor().equals(id))
                .findFirst()
                .orElse(null);

        if (repartidorExistente == null) {
            mostrarMensaje("No se encontró el repartidor con ID: " + id);
            return;
        }

        // Validar campos
        if (txtNombre.getText().isBlank() || txtTelefono.getText().isBlank() || 
            cmbZonaCobertura.getValue() == null) {
            mostrarMensaje("Por favor complete todos los campos obligatorios.");
            return;
        }

        // Crear DTO actualizado
        // Obtener el estado seleccionado o mantener el estado existente
        co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor estado = repartidorExistente.estado();
        if (cmbEstado.getValue() != null) {
            try {
                estado = co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor.valueOf(
                        cmbEstado.getValue().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Mantener el estado existente si hay error
            }
        }
        
        RepartidorDto actualizado = new RepartidorDto(
                id,
                txtNombre.getText(),
                txtTelefono.getText(),
                txtVehiculo.getText().isBlank() ? "Sin asignar" : txtVehiculo.getText(),
                cmbZonaCobertura.getValue(),
                estado
        );

        // Actualizar en el modelo
        boolean actualizadoExitoso = model.actualizarRepartidor(actualizado);
        if (actualizadoExitoso) {
            cargarRepartidores(); // Recargar la lista para reflejar cambios
            mostrarMensaje("Repartidor actualizado correctamente.");
            limpiarCampos();
        } else {
            mostrarMensaje("No se pudo actualizar el repartidor.");
        }
    }

    @FXML
    private void onEliminarRepartidor() {
        RepartidorDto seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un repartidor de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar el repartidor?");
        confirmacion.setContentText("ID: " + seleccionado.idRepartidor() + "\nNombre: " + seleccionado.nombre());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                listaRepartidores.remove(seleccionado);
                limpiarCampos();
                mostrarMensaje("Repartidor eliminado correctamente.");
            }
        });
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtVehiculo.clear();
        cmbEstado.setValue(null);
        cmbZonaCobertura.setValue(null);
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
