package co.edu.uniquindio.citycourier.citycourier.viewController;

import co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloViewController {

    private final UsuarioController usuarioController = new UsuarioController();

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

            Direccion origen = new Direccion("O1", "Centro", "Calle 1 #2-3", "Armenia", "Frente al parque");
            Direccion destino = new Direccion("D1", "Norte", "Carrera 10 #20-30", "Armenia", "Edificio azul");

            Envio creado = usuarioController.crearEnvio("U1", origen, destino, desc, peso, volumen, distancia, prioridad);
            println("Envío creado: id=" + creado.getIdEnvio() + ", costo=$" + (long) creado.getCosto());
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
        for (Envio e : lista) {
            println("- id=" + e.getIdEnvio() + " estado=" + e.getEstado() + " costo=$" + (long) e.getCosto());
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