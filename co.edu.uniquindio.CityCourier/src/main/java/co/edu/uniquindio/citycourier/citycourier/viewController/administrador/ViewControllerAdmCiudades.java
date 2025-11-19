package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewControllerAdmCiudades {

    @FXML private TableView<String> tablaCiudades;
    @FXML private TableColumn<String, String> colIdCiudad;
    @FXML private TableColumn<String, String> colNombreCiudad;
    @FXML private TableColumn<String, String> colDepartamento;

    @FXML private TextField txtIdCiudad;
    @FXML private TextField txtNombreCiudad;
    @FXML private TextField txtHabitantes;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<String> listaCiudades = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas para mostrar datos de forma simple
        colIdCiudad.setCellValueFactory(data -> {
            String ciudad = data.getValue();
            if (ciudad != null && ciudad.contains(" | ")) {
                return new javafx.beans.property.SimpleStringProperty(ciudad.split(" \\| ")[0]);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colNombreCiudad.setCellValueFactory(data -> {
            String ciudad = data.getValue();
            if (ciudad != null && ciudad.contains(" | ")) {
                String[] partes = ciudad.split(" \\| ");
                if (partes.length > 1) {
                    return new javafx.beans.property.SimpleStringProperty(partes[1]);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colDepartamento.setCellValueFactory(data -> {
            String ciudad = data.getValue();
            if (ciudad != null && ciudad.contains(" | ")) {
                String[] partes = ciudad.split(" \\| ");
                if (partes.length > 2) {
                    return new javafx.beans.property.SimpleStringProperty(partes[2]);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        tablaCiudades.setItems(listaCiudades);
        
        // Cargar ciudades desde el modelo
        cargarCiudades();
        
        // Listener para selección de tabla
        tablaCiudades.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarDatosCiudad(newVal);
            }
        });
    }
    
    private void cargarCiudades() {
        listaCiudades.setAll(model.listarCiudades());
    }

    private void cargarDatosCiudad(String ciudad) {
        if (ciudad != null && ciudad.contains(" | ")) {
            String[] partes = ciudad.split(" \\| ");
            if (partes.length >= 3) {
                txtIdCiudad.setText(partes[0]);
                txtNombreCiudad.setText(partes[1]);
                txtHabitantes.setText(partes[2]);
            }
        }
    }

    @FXML
    private void onRegistrarCiudad() {
        String id = txtIdCiudad.getText();
        String nombre = txtNombreCiudad.getText();
        String habitantes = txtHabitantes.getText();

        if (id.isBlank() || nombre.isBlank() || habitantes.isBlank()) {
            mostrarMensaje("Por favor complete todos los campos.");
            return;
        }

        // Verificar si ya existe una ciudad con ese ID
        boolean existe = listaCiudades.stream()
                .anyMatch(c -> c != null && c.startsWith(id + " | "));
        if (existe) {
            mostrarMensaje("Ya existe una ciudad con ese ID.");
            return;
        }

        String ciudad = id + " | " + nombre + " | " + habitantes;
        // Agregar al modelo
        boolean agregada = model.agregarCiudad(ciudad);
        if (agregada) {
            cargarCiudades(); // Recargar desde el modelo
            limpiarCampos();
            mostrarMensaje("Ciudad registrada correctamente.");
        } else {
            mostrarMensaje("No se pudo agregar la ciudad (ya existe).");
        }
    }

    @FXML
    private void onEliminarCiudad() {
        String seleccionada = tablaCiudades.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Por favor seleccione una ciudad para eliminar.");
            return;
        }
        listaCiudades.remove(seleccionada);
        mostrarMensaje("Ciudad eliminada correctamente.");
    }

    @FXML
    private void onActualizarCiudad() {
        String id = txtIdCiudad.getText();
        if (id.isBlank()) {
            mostrarMensaje("Seleccione una ciudad de la tabla para actualizar.");
            return;
        }

        String nombre = txtNombreCiudad.getText();
        String habitantes = txtHabitantes.getText();

        if (nombre.isBlank() || habitantes.isBlank()) {
            mostrarMensaje("Por favor complete todos los campos.");
            return;
        }

        // Buscar y actualizar la ciudad
        String ciudadActualizada = id + " | " + nombre + " | " + habitantes;
        for (int i = 0; i < listaCiudades.size(); i++) {
            if (listaCiudades.get(i).startsWith(id + " | ")) {
                listaCiudades.set(i, ciudadActualizada);
                mostrarMensaje("Ciudad actualizada correctamente.");
                limpiarCampos();
                return;
            }
        }
        mostrarMensaje("No se encontró la ciudad con ID: " + id);
    }

    private void limpiarCampos() {
        txtIdCiudad.clear();
        txtNombreCiudad.clear();
        txtHabitantes.clear();
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

