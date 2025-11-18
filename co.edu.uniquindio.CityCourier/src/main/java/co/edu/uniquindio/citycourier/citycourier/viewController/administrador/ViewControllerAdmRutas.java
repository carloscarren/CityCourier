package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewControllerAdmRutas {

    @FXML private TableView<String> tablaRutas;
    @FXML private TableColumn<String, String> colIdRuta;
    @FXML private TableColumn<String, String> colOrigen;
    @FXML private TableColumn<String, String> colDestino;
    @FXML private TableColumn<String, String> colDistancia;

    @FXML private TextField txtIdRuta;
    @FXML private ComboBox<String> cmbCiudadOrigen;
    @FXML private ComboBox<String> cmbCiudadDestino;
    @FXML private TextField txtDistancia;

    private final ObservableList<String> listaRutas = FXCollections.observableArrayList();
    private final ObservableList<String> ciudades = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Cargar ciudades disponibles
        ciudades.addAll("Armenia", "Quimbaya", "Circasia", "Montenegro", "Tebaida", "Pereira", "Manizales");
        cmbCiudadOrigen.setItems(ciudades);
        cmbCiudadDestino.setItems(ciudades);
        
        // Cargar rutas iniciales
        listaRutas.add("0000 | Quimbaya -> Armenia | 22.2");
        listaRutas.add("0001 | Quimbaya -> Montenegro | 11.0");
        listaRutas.add("0002 | Circasia -> Quimbaya | 26.8");
        listaRutas.add("0003 | Circasia -> Armenia | 12.8");
        listaRutas.add("0004 | Quimbaya -> Tebaida | 28.9");
        // Configurar columnas para mostrar datos de forma simple
        colIdRuta.setCellValueFactory(data -> {
            String ruta = data.getValue();
            if (ruta != null && ruta.contains(" | ")) {
                return new javafx.beans.property.SimpleStringProperty(ruta.split(" \\| ")[0]);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colOrigen.setCellValueFactory(data -> {
            String ruta = data.getValue();
            if (ruta != null && ruta.contains(" -> ")) {
                String parte = ruta.split(" -> ")[0];
                if (parte.contains(" | ")) {
                    return new javafx.beans.property.SimpleStringProperty(parte.split(" \\| ", 2)[1]);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colDestino.setCellValueFactory(data -> {
            String ruta = data.getValue();
            if (ruta != null && ruta.contains(" -> ")) {
                String parte = ruta.split(" -> ")[1];
                if (parte.contains(" (")) {
                    return new javafx.beans.property.SimpleStringProperty(parte.split(" \\(")[0]);
                }
                return new javafx.beans.property.SimpleStringProperty(parte);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colDistancia.setCellValueFactory(data -> {
            String ruta = data.getValue();
            if (ruta != null && ruta.contains(" (")) {
                String distancia = ruta.split(" \\(")[1].replace(" km)", "");
                return new javafx.beans.property.SimpleStringProperty(distancia);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        tablaRutas.setItems(listaRutas);
        
        // Listener para selección de tabla
        tablaRutas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarDatosRuta(newVal);
            }
        });
    }

    private void cargarDatosRuta(String ruta) {
        if (ruta != null && ruta.contains(" | ")) {
            String[] partes = ruta.split(" \\| ");
            if (partes.length >= 3) {
                txtIdRuta.setText(partes[0]);
                // Parsear origen y destino
                String origenDestino = partes[1];
                if (origenDestino.contains(" -> ")) {
                    String[] ciudades = origenDestino.split(" -> ");
                    cmbCiudadOrigen.setValue(ciudades[0].trim());
                    cmbCiudadDestino.setValue(ciudades[1].trim());
                }
                txtDistancia.setText(partes[2]);
            }
        }
    }

    @FXML
    private void onRegistrarRuta() {
        String id = txtIdRuta.getText();
        String origen = cmbCiudadOrigen.getValue();
        String destino = cmbCiudadDestino.getValue();
        String distancia = txtDistancia.getText();

        if (id.isBlank() || origen == null || destino == null || distancia.isBlank()) {
            mostrarMensaje("Por favor complete todos los campos.");
            return;
        }

        if (origen.equals(destino)) {
            mostrarMensaje("La ciudad origen y destino no pueden ser la misma.");
            return;
        }

        // Verificar si ya existe una ruta con ese ID
        boolean existe = listaRutas.stream()
                .anyMatch(r -> r.startsWith(id + " | "));
        if (existe) {
            mostrarMensaje("Ya existe una ruta con ese ID.");
            return;
        }

        String ruta = id + " | " + origen + " -> " + destino + " | " + distancia;
        listaRutas.add(ruta);
        limpiarCampos();
        mostrarMensaje("Ruta registrada correctamente.");
    }

    @FXML
    private void onEliminarRuta() {
        String seleccionada = tablaRutas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor seleccione una ruta para eliminar.");
            return;
        }
        listaRutas.remove(seleccionada);
        mostrarMensaje("Ruta eliminada correctamente.");
    }

    @FXML
    private void onActualizarRuta() {
        String id = txtIdRuta.getText();
        if (id.isBlank()) {
            mostrarMensaje("Seleccione una ruta de la tabla para actualizar.");
            return;
        }

        String origen = cmbCiudadOrigen.getValue();
        String destino = cmbCiudadDestino.getValue();
        String distancia = txtDistancia.getText();

        if (origen == null || destino == null || distancia.isBlank()) {
            mostrarMensaje("Por favor complete todos los campos.");
            return;
        }

        // Buscar y actualizar la ruta
        String rutaActualizada = id + " | " + origen + " -> " + destino + " | " + distancia;
        for (int i = 0; i < listaRutas.size(); i++) {
            if (listaRutas.get(i).startsWith(id + " | ")) {
                listaRutas.set(i, rutaActualizada);
                mostrarMensaje("Ruta actualizada correctamente.");
                limpiarCampos();
                return;
            }
        }
        mostrarMensaje("No se encontró la ruta con ID: " + id);
    }

    private void limpiarCampos() {
        txtIdRuta.clear();
        cmbCiudadOrigen.setValue(null);
        cmbCiudadDestino.setValue(null);
        txtDistancia.clear();
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

