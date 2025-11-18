package co.edu.uniquindio.citycourier.citycourier.viewController.usuario;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UsuarioViewController {

    private final co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController usuarioController = new co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController();

    @FXML
    private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtDistancia;
    @FXML private CheckBox chkPrioridad;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtIdCancelar;
    @FXML private TextArea txtSalida;

    @FXML
    protected void onCotizar() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            double costo = usuarioController.cotizar(peso, volumen, distancia, prioridad);
            println("Costo estimado: $" + (long) costo);
        } catch (Exception e) {
            println("Error al cotizar: " + e.getMessage());
        }
    }

    @FXML
    protected void onCrearEnvio() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            String desc = txtDescripcion.getText();

            Direccion origen = new Direccion("Calle 1 #2-3, Centro", "Armenia", "Frente al parque");
            Direccion destino = new Direccion("Carrera 10 #20-30, Norte", "Armenia", "Edificio azul");

            EnvioDto creado = usuarioController.crearEnvio("U1", origen, destino, desc, peso, volumen, distancia, prioridad);
            println("Envío creado: id=" + creado.idEnvio() + ", costo=$" + (long) creado.costo());
        } catch (Exception e) {
            println("Error al crear envío: " + e.getMessage());
        }
    }

    @FXML
    protected void onListar() {
        var lista = usuarioController.listarEnvios();
        if (lista.isEmpty()) {
            println("No hay envíos.");
            return;
        }
        println("Envíos:");
        for (EnvioDto e : lista) {
            println("- id=" + e.idEnvio() + " estado=" + e.estado() + " costo=$" + (long) e.costo());
        }
    }

    @FXML
    protected void onCancelar() {
        String id = txtIdCancelar.getText();
        if (id == null || id.isBlank()) {
            println("Ingrese el ID del envío a cancelar.");
            return;
        }
        boolean ok = usuarioController.cancelarEnvio(id);
        println(ok ? "Envío cancelado." : "No se pudo cancelar (no existe o ya está asignado/en ruta).");
    }

    private void println(String s) {
        txtSalida.appendText(s + "\n");
    }
}