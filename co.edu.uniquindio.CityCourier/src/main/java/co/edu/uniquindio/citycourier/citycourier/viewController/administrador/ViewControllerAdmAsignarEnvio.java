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
        
        // Cargar datos iniciales
        cargarDatosIniciales();
        
        // Agregar listener para refrescar cuando el nodo se vuelve visible
        if (cmbRepartidor != null && cmbRepartidor.getScene() != null) {
            cmbRepartidor.getScene().getRoot().visibleProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    refrescarDatos();
                }
            });
        }

        // Configurar ComboBox de pedidos
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
    
    /**
     * Carga las asignaciones existentes en la tabla
     */
    private void cargarAsignacionesExistentes() {
        listaAsignaciones.clear();
        var todosEnvios = model.listarEnvios();
        var todosRepartidores = model.listarRepartidores();
        
        for (EnvioDto envio : todosEnvios) {
            if (envio.idRepartidor() != null && !envio.idRepartidor().isBlank()) {
                var repartidor = todosRepartidores.stream()
                        .filter(r -> r.idRepartidor().equals(envio.idRepartidor()))
                        .findFirst();
                if (repartidor.isPresent()) {
                    String asignacion = envio.idEnvio() + " | " + 
                            repartidor.get().nombre() + " (" + repartidor.get().idRepartidor() + ")";
                    listaAsignaciones.add(asignacion);
                }
            }
        }
    }
    
    /**
     * Carga todos los datos iniciales
     */
    private void cargarDatosIniciales() {
        cargarAsignacionesExistentes();
        cargarRepartidores();
        cargarEnviosDisponibles();
    }
    
    /**
     * Recarga la lista de repartidores (útil cuando se crea uno nuevo)
     */
    private void cargarRepartidores() {
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
            // Recargar asignaciones para incluir la nueva
            cargarAsignacionesExistentes();
            
            // Actualizar lista de pedidos disponibles (remover el asignado)
            cargarEnviosDisponibles();
            
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
    
    /**
     * Método público para refrescar los datos cuando se cambia a esta pestaña
     * Útil cuando se crea un nuevo repartidor
     */
    public void refrescarDatos() {
        cargarRepartidores();
        cargarEnviosDisponibles();
        cargarAsignacionesExistentes();
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

