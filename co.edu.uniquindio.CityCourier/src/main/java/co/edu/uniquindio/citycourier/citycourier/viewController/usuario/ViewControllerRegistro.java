package co.edu.uniquindio.citycourier.citycourier.viewController.usuario;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewControllerRegistro {

    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private PasswordField txtConfirmarContrasena;
    @FXML
    private ComboBox<String> cmbTipoUsuario;

    private final ModelCityCourier model = ModelCityCourier.getInstance();

    @FXML
    public void initialize() {
        // Configurar tipos de usuario disponibles
        cmbTipoUsuario.setItems(FXCollections.observableArrayList("CLIENTE", "ADMINISTRADOR"));
        cmbTipoUsuario.setValue("CLIENTE"); // Valor por defecto
    }

    @FXML
    private void onRegistrar() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Validar que las contraseñas coincidan
        if (!txtContrasena.getText().equals(txtConfirmarContrasena.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return;
        }

        // Validar que el correo no esté ya registrado
        String correo = txtCorreo.getText().trim().toLowerCase();
        if (correoYaExiste(correo)) {
            mostrarAlerta("Error", "El correo ya está registrado en el sistema", Alert.AlertType.ERROR);
            return;
        }

        // Validar que el ID no esté ya registrado
        String idUsuario = txtIdUsuario.getText().trim();
        if (idUsuarioYaExiste(idUsuario)) {
            mostrarAlerta("Error", "El ID de usuario ya está registrado", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Obtener tipo de usuario
            tipoUsuario tipo = tipoUsuario.valueOf(cmbTipoUsuario.getValue());

            // Crear objeto Usuario (con contraseña)
            Usuario nuevoUsuario = new Usuario(
                    idUsuario,
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    correo,
                    txtTelefono.getText().trim(),
                    txtContrasena.getText(),
                    tipo
            );

            // Registrar en el modelo (tanto en usuariosModelo como en usuarios DTO)
            boolean registrado = model.registrarUsuario(nuevoUsuario);

            if (registrado) {
                mostrarAlerta("Éxito", "Usuario registrado", Alert.AlertType.INFORMATION);
                // Limpiar campos
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo registrar el usuario. Verifique que el ID o correo no estén ya registrados.", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Tipo de usuario inválido", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancelar() {
        try {
            Stage stage = (Stage) txtIdUsuario.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();

            java.net.URL url = getClass().getResource(
                    "/co/edu/uniquindio/citycourier/citycourier/Login.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró el archivo Login.fxml", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("CityCourier - Iniciar Sesión");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de login: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (txtIdUsuario.getText().isBlank()) {
            mostrarAlerta("Error", "El ID de usuario es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (txtNombre.getText().isBlank()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (txtApellido.getText().isBlank()) {
            mostrarAlerta("Error", "El apellido es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (txtCorreo.getText().isBlank()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (!txtCorreo.getText().contains("@")) {
            mostrarAlerta("Error", "El correo debe tener un formato válido", Alert.AlertType.ERROR);
            return false;
        }
        if (txtTelefono.getText().isBlank()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (txtContrasena.getText().isBlank()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (txtContrasena.getText().length() < 4) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 4 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        if (cmbTipoUsuario.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de usuario", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private boolean correoYaExiste(String correo) {
        return model.listarUsuarios().stream()
                .anyMatch(u -> u.correo() != null && u.correo().trim().toLowerCase().equals(correo));
    }

    private boolean idUsuarioYaExiste(String idUsuario) {
        return model.listarUsuarios().stream()
                .anyMatch(u -> u.idUsuario() != null && u.idUsuario().equals(idUsuario));
    }

    private void limpiarCampos() {
        txtIdUsuario.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
        cmbTipoUsuario.setValue("CLIENTE");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo, mensaje, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

