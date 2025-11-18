package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class ViewControllerAdmAsignarEnvio {

    @FXML private TableView<String> tablaAsignaciones;
    @FXML private TableColumn<String, String> colRutaAsignacion;
    @FXML private TableColumn<String, String> colIdPedidoAsignacion;

    @FXML private ComboBox<RepartidorDto> cmbRepartidor;
    @FXML private ComboBox<EnvioDto> cmbPedido;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<String> listaAsignaciones = FXCollections.observableArrayList();
    private final ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();
    private final ObservableList<EnvioDto> listaPedidos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar tabla de asignaciones
        colIdPedidoAsignacion.setCellValueFactory(data -> {
            String asignacion = data.getValue();
            if (asignacion != null && asignacion.contains(" | ")) {
                return new javafx.beans.property.SimpleStringProperty(asignacion.split(" \\| ")[0]);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colRutaAsignacion.setCellValueFactory(data -> {
            String asignacion = data.getValue();
            if (asignacion != null && asignacion.contains(" | ")) {
                String[] partes = asignacion.split(" \\| ");
                if (partes.length > 1) {
                    return new javafx.beans.property.SimpleStringProperty(partes[1]);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        tablaAsignaciones.setItems(listaAsignaciones);

        // Cargar SOLO repartidores disponibles (ACTIVO)
        // Nota: Por ahora cargamos todos, pero en un sistema real se filtrarían por estado ACTIVO
        listaRepartidores.setAll(model.listarRepartidores());
        cmbRepartidor.setItems(listaRepartidores);
        cmbRepartidor.setConverter(new StringConverter<RepartidorDto>() {
            @Override
            public String toString(RepartidorDto repartidor) {
                return repartidor != null ? repartidor.nombre() + " (" + repartidor.idRepartidor() + ")" : "";
            }

            @Override
            public RepartidorDto fromString(String string) {
                return null;
            }
        });

        // Cargar SOLO pedidos disponibles (envíos en estado SOLICITANDO)
        cargarEnviosDisponibles();

        cmbPedido.setItems(listaPedidos);
        cmbPedido.setConverter(new StringConverter<EnvioDto>() {
            @Override
            public String toString(EnvioDto envio) {
                return envio != null ? envio.idEnvio() + " - " + envio.direccionOrigen() + " -> " + envio.direccionDestino() : "";
            }

            @Override
            public EnvioDto fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Carga solo los envíos en estado SOLICITANDO (disponibles para asignar)
     */
    private void cargarEnviosDisponibles() {
        var todosEnvios = model.listarEnvios();
        listaPedidos.setAll(
                todosEnvios.stream()
                        .filter(e -> e.getEstado() == estadoEnvio.SOLICITANDO)
                        .toList()
        );
    }

    // =============================================================
    // ASIGNAR ENVÍO A REPARTIDOR
    // Responsabilidad Única: ÚNICA pestaña que asigna envíos a repartidores
    // =============================================================
    @FXML
    private void onAsignarEnvio() {
        RepartidorDto repartidor = cmbRepartidor.getValue();
        EnvioDto pedido = cmbPedido.getValue();

        if (repartidor == null || pedido == null) {
            mostrarMensaje("Por favor seleccione un repartidor y un envío.");
            return;
        }

        // Validar que el envío esté en estado SOLICITANDO
        if (pedido.getEstado() != estadoEnvio.SOLICITANDO) {
            mostrarMensaje("Solo se pueden asignar envíos en estado SOLICITANDO.\n" +
                    "Estado actual del envío: " + pedido.getEstado());
            return;
        }

        // Asignar envío al repartidor (esto cambia el estado a EN_RUTA)
        boolean asignado = model.asignarRepartidorEnvio(pedido.idEnvio(), repartidor.idRepartidor());
        if (asignado) {
            // Agregar a la tabla de asignaciones
            String asignacion = pedido.idEnvio() + " | " + repartidor.nombre() + " (" + repartidor.idRepartidor() + ")";
            listaAsignaciones.add(asignacion);
            
            // Actualizar lista de pedidos disponibles (remover el asignado)
            listaPedidos.remove(pedido);
            
            mostrarMensaje("Envío asignado correctamente al repartidor.\n" +
                    "Envío: " + pedido.idEnvio() + "\n" +
                    "Repartidor: " + repartidor.nombre() + " (" + repartidor.idRepartidor() + ")\n" +
                    "Estado actualizado: SOLICITANDO → EN_RUTA");
            limpiarCampos();
        } else {
            mostrarMensaje("No se pudo asignar el envío.\n" +
                    "Verifique que el envío esté en estado SOLICITANDO y que el repartidor exista.");
        }
    }

    /**
     * Método para refrescar la lista de envíos disponibles
     * Útil cuando se regresa a esta pestaña después de crear nuevos envíos
     */
    public void refrescarEnviosDisponibles() {
        cargarEnviosDisponibles();
    }

    private void limpiarCampos() {
        cmbRepartidor.setValue(null);
        cmbPedido.setValue(null);
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

