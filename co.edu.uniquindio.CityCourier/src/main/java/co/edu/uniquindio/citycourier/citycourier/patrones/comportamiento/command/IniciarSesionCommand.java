package co.edu.uniquindio.citycourier.citycourier.patrones.comportamiento.command;

import co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.tipoUsuario;
import co.edu.uniquindio.citycourier.citycourier.viewController.administrador.ViewControllerAdmnistrador;
import co.edu.uniquindio.citycourier.citycourier.viewController.usuario.UsuarioViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IniciarSesionCommand implements Command {

    private final String correo;
    private final String clave;
    private final Stage stage;

    public IniciarSesionCommand(String correo, String clave, Stage stage) {
        this.correo = correo;
        this.clave = clave;
        this.stage = stage;
    }

    @Override
    public void execute() {
        // TODO: Implementar método de autenticación en UsuarioController
        // Por ahora, este es un placeholder - necesitas implementar autenticarUsuario en UsuarioController
        try {
            // Ejemplo de cómo debería funcionar (necesitas implementar el método):
            // UsuarioController usuarioController = new UsuarioController();
            // UsuarioDto usuario = usuarioController.autenticarUsuario(correo, clave);
            
            // Por ahora, simulamos la autenticación
            UsuarioDto usuario = null; // Esto debe venir de la autenticación real
            
            if (usuario != null) {
                if (usuario.tipo() == tipoUsuario.ADMINISTRADOR) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "/co/edu/uniquindio/citycourier/citycourier/AdministradorView.fxml"));
                    Parent root = loader.load();

                    ViewControllerAdmnistrador controller = loader.getController();
                    // controller.inicializarAdministrador(usuario.nombre()); // Si existe este método

                    stage.setScene(new Scene(root));
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "/co/edu/uniquindio/citycourier/citycourier/VistaUsuario.fxml"));
                    Parent root = loader.load();

                    UsuarioViewController controller = loader.getController();
                    // controller.inicializarUsuario(usuario.nombre()); // Si existe este método

                    stage.setScene(new Scene(root));
                }
            } else {
                mostrarAlerta("Error", "Correo o clave incorrectos.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}