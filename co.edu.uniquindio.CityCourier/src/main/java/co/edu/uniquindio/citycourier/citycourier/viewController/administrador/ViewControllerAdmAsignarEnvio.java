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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewControllerAdmAsignarEnvio {

    @FXML private TableView<String> tablaAsignaciones;
    @FXML private TableColumn<String, String> colRutaAsignacion;
    @FXML private TableColumn<String, String> colIdPedidoAsignacion;

    @FXML private TextField txtIdEnvio;
    @FXML private DatePicker datePickerFechaSalida;
    @FXML private ComboBox<String> cmbRuta;
    @FXML private ComboBox<RepartidorDto> cmbRepartidor;
    @FXML private ComboBox<EnvioDto> cmbPedido;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<String> listaAsignaciones = FXCollections.observableArrayList();
    private final ObservableList<String> listaRutas = FXCollections.observableArrayList();
    private final ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();
    private final ObservableList<EnvioDto> listaPedidos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar tabla de asignaciones
        colRutaAsignacion.setCellValueFactory(data -> {
            String asignacion = data.getValue();
            if (asignacion != null && asignacion.contains(" | ")) {
                return new javafx.beans.property.SimpleStringProperty(asignacion.split(" \\| ")[0]);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colIdPedidoAsignacion.setCellValueFactory(data -> {
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

        // Cargar rutas disponibles
        listaRutas.addAll("Quimbaya -> Armenia", "Quimbaya -> Montenegro", 
                         "Circasia -> Quimbaya", "Circasia -> Armenia", 
                         "Quimbaya -> Tebaida");
        cmbRuta.setItems(listaRutas);

        // Cargar repartidores
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

        // Cargar pedidos disponibles (envíos en estado SOLICITANDO)
        var todosEnvios = model.listarEnvios();
        listaPedidos.setAll(
                todosEnvios.stream()
                        .filter(e -> e.getEstado() == estadoEnvio.SOLICITANDO)
                        .toList()
        );
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

        // Configurar fecha por defecto
        datePickerFechaSalida.setValue(LocalDate.now());
    }

    @FXML
    private void onAsignarEnvio() {
        String ruta = cmbRuta.getValue();
        RepartidorDto repartidor = cmbRepartidor.getValue();
        EnvioDto pedido = cmbPedido.getValue();
        LocalDate fechaSalida = datePickerFechaSalida.getValue();

        if (ruta == null || repartidor == null || pedido == null || fechaSalida == null) {
            mostrarMensaje("Por favor complete todos los campos.");
            return;
        }

        // Asignar envío al repartidor
        boolean asignado = model.asignarRepartidorEnvio(pedido.idEnvio(), repartidor.idRepartidor());
        if (asignado) {
            // Agregar a la tabla de asignaciones
            String asignacion = ruta + " | " + pedido.idEnvio();
            listaAsignaciones.add(asignacion);
            
            // Actualizar lista de pedidos disponibles
            listaPedidos.remove(pedido);
            
            mostrarMensaje("Envío asignado correctamente al repartidor.");
            limpiarCampos();
        } else {
            mostrarMensaje("No se pudo asignar el envío (ID inválido o envío ya finalizado).");
        }
    }

    @FXML
    private void onCrearEnvio() {
        String ruta = cmbRuta.getValue();
        RepartidorDto repartidor = cmbRepartidor.getValue();
        EnvioDto pedido = cmbPedido.getValue();
        LocalDate fechaSalida = datePickerFechaSalida.getValue();

        if (ruta == null || repartidor == null || pedido == null || fechaSalida == null) {
            mostrarMensaje("Por favor complete todos los campos para crear el envío.");
            return;
        }

        // Crear asignación
        String asignacion = ruta + " | " + pedido.idEnvio();
        listaAsignaciones.add(asignacion);
        
        // Asignar envío al repartidor
        model.asignarRepartidorEnvio(pedido.idEnvio(), repartidor.idRepartidor());
        
        // Actualizar lista de pedidos disponibles
        listaPedidos.remove(pedido);
        
        mostrarMensaje("Envío creado y asignado correctamente.");
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIdEnvio.clear();
        datePickerFechaSalida.setValue(LocalDate.now());
        cmbRuta.setValue(null);
        cmbRepartidor.setValue(null);
        cmbPedido.setValue(null);
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

