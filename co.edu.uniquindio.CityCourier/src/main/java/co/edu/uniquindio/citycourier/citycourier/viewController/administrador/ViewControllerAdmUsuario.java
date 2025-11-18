package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ViewControllerAdmUsuario {

    @FXML private TableView<UsuarioDto> tablaUsuarios;
    @FXML private TableColumn<UsuarioDto, String> colId;
    @FXML private TableColumn<UsuarioDto, String> colNombre;
    @FXML private TableColumn<UsuarioDto, String> colCorreo;
    @FXML private TableColumn<UsuarioDto, String> colTelefono;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;

    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private final ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().idUsuario()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().nombre()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().correo()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().telefono()));

        tablaUsuarios.setItems(listaUsuarios);
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios.setAll(model.listarUsuarios());
    }

    @FXML
    private void onAgregarUsuario() {
        try {
            UsuarioDto nuevo = new UsuarioDto(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtTelefono.getText(),
                    co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario.CLIENTE,
                    java.util.List.of("Dirección por defecto")
            );

            boolean creado = model.crearUsuario(nuevo);

            if (creado) {
                cargarUsuarios();
                limpiarCampos();
                mostrarMensaje("Usuario creado con éxito.");
            } else {
                mostrarMensaje("No se pudo crear el usuario (usuario ya existe).");
            }

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }


    @FXML
    private void onEliminarUsuario() {
        String id = txtId.getText();
        if (id.isBlank()) {
            mostrarMensaje("Ingrese el ID del usuario a eliminar.");
            return;
        }
        boolean eliminado = model.eliminarUsuario(id);
        mostrarMensaje(eliminado ? "Usuario eliminado correctamente." : "No se encontró el usuario.");
        cargarUsuarios();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

