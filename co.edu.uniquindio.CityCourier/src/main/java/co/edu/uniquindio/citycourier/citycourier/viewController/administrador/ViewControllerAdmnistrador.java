package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.controller.AdministradorController;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.RepartidorDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewControllerAdmnistrador {

    private final AdministradorController administradorController = new AdministradorController();

    @FXML
    private TextArea txtSalida;
    @FXML private TextField txtIdEnvio;
    @FXML private TextField txtNuevoEstado;
    @FXML private TextField txtIdRepartidor;

    @FXML
    protected void onListarEnvios() {
        var lista = administradorController.listarEnvios();
        txtSalida.clear();
        if (lista.isEmpty()) {
            txtSalida.appendText("No hay envíos registrados.\n");
            return;
        }
        txtSalida.appendText("📦 Lista de envíos:\n");
        for (EnvioDto e : lista) {
            txtSalida.appendText("- ID: " + e.idEnvio() + " | Costo: $" + (long)e.costo() + "\n");
        }
    }

    @FXML
    protected void onActualizarEstado() {
        String idEnvio = txtIdEnvio.getText();
        String estado = txtNuevoEstado.getText();
        if (administradorController.actualizarEstadoEnvio(idEnvio, estado)) {
            txtSalida.appendText("✅ Estado actualizado correctamente.\n");
        } else {
            txtSalida.appendText("❌ Error al actualizar estado.\n");
        }
    }

    @FXML
    protected void onAsignarRepartidor() {
        String idEnvio = txtIdEnvio.getText();
        String idRepartidor = txtIdRepartidor.getText();
        if (administradorController.asignarRepartidor(idEnvio, idRepartidor)) {
            txtSalida.appendText("🚚 Repartidor asignado correctamente.\n");
        } else {
            txtSalida.appendText("⚠️ No se pudo asignar el repartidor.\n");
        }
    }

    @FXML
    protected void onListarRepartidores() {
        var lista = administradorController.listarRepartidores();
        txtSalida.appendText("👷‍♂️ Repartidores disponibles:\n");
        for (RepartidorDto r : lista) {
            txtSalida.appendText("- " + r.nombre() + " (Vehículo: " + r.vehiculoAsignado() + ")\n");
        }
    }
}