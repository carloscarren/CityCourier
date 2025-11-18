package co.edu.uniquindio.citycourier.citycourier.viewController.usuario;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.UsuarioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioViewController {

    private final co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController usuarioController = new co.edu.uniquindio.citycourier.citycourier.controller.UsuarioController();
    private final ModelCityCourier model = ModelCityCourier.getInstance();
    private UsuarioDto usuarioActual;

    @FXML
    private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtDistancia;
    @FXML private CheckBox chkPrioridad;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtIdCancelar;
    @FXML private TextArea txtSalida;
    @FXML
    private javafx.scene.control.Button btnCerrarSesion;
    @FXML
    private javafx.scene.control.Label lblNombreUsuario;
    @FXML
    private TextField txtIdRastrear;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextArea txtInfoRastreo;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtReferencia;
    @FXML
    private ListView<String> listaDirecciones;
    
    // Campos del perfil (pueden estar en el FXML incluido, así que los buscaremos dinámicamente)
    private Label lblNombrePerfil;
    private Label lblCorreoPerfil;
    private Label lblTelefonoPerfil;
    private Label lblIdUsuarioPerfil;
    private Label lblDireccion1;
    private Label lblDireccion2;
    private Label lblTotalEnvios;
    private Label lblEnviosPendientes;
    private Label lblEnviosCompletados;
    
    private final ObservableList<String> direcciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicializar lista de direcciones
        if (listaDirecciones != null) {
            listaDirecciones.setItems(direcciones);
        }
        
        // Agregar listener para actualizar estadísticas cuando se selecciona la pestaña de Perfil
        if (tabPane != null) {
            tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && "Perfil".equals(newValue.getText())) {
                    // Usar Platform.runLater con un pequeño delay para asegurar que el FXML incluido esté completamente cargado
                    Platform.runLater(() -> {
                        // Forzar búsqueda de nodos (resetear referencias primero)
                        lblNombrePerfil = null;
                        lblCorreoPerfil = null;
                        lblTelefonoPerfil = null;
                        lblIdUsuarioPerfil = null;
                        lblDireccion1 = null;
                        lblDireccion2 = null;
                        lblTotalEnvios = null;
                        lblEnviosPendientes = null;
                        lblEnviosCompletados = null;
                        
                        // Buscar nodos y actualizar
                        buscarNodosPerfil();
                        actualizarPerfil();
                    });
                }
            });
        }
    }
    
    /**
     * Busca los nodos del perfil en el FXML incluido
     */
    private void buscarNodosPerfil() {
        if (tabPane == null) return;
        
        // Primero intentar buscar en la escena (método más rápido)
        javafx.scene.Scene escena = tabPane.getScene();
        if (escena != null && escena.getRoot() != null) {
            // Buscar los labels usando lookup en la escena
            Node nodo = escena.getRoot().lookup("#lblNombrePerfil");
            if (nodo != null) {
                lblNombrePerfil = (Label) nodo;
                lblCorreoPerfil = (Label) escena.getRoot().lookup("#lblCorreoPerfil");
                lblTelefonoPerfil = (Label) escena.getRoot().lookup("#lblTelefonoPerfil");
                lblIdUsuarioPerfil = (Label) escena.getRoot().lookup("#lblIdUsuarioPerfil");
                lblDireccion1 = (Label) escena.getRoot().lookup("#lblDireccion1");
                lblDireccion2 = (Label) escena.getRoot().lookup("#lblDireccion2");
                lblTotalEnvios = (Label) escena.getRoot().lookup("#lblTotalEnvios");
                lblEnviosPendientes = (Label) escena.getRoot().lookup("#lblEnviosPendientes");
                lblEnviosCompletados = (Label) escena.getRoot().lookup("#lblEnviosCompletados");
                return;
            }
        }
        
        // Si no se encontró en la escena, buscar en todas las pestañas
        for (javafx.scene.control.Tab tab : tabPane.getTabs()) {
            if ("Perfil".equals(tab.getText())) {
                Node contenidoPerfil = tab.getContent();
                if (contenidoPerfil != null) {
                    buscarNodosRecursivo(contenidoPerfil);
                    // Si encontramos todos los nodos, salir
                    if (lblTotalEnvios != null && lblEnviosPendientes != null && lblEnviosCompletados != null) {
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Busca nodos recursivamente en un contenedor
     */
    private void buscarNodosRecursivo(Node nodo) {
        if (nodo == null) return;
        
        // Buscar por fx:id
        if (nodo.getId() != null) {
            switch (nodo.getId()) {
                case "lblNombrePerfil":
                    lblNombrePerfil = (Label) nodo;
                    break;
                case "lblCorreoPerfil":
                    lblCorreoPerfil = (Label) nodo;
                    break;
                case "lblTelefonoPerfil":
                    lblTelefonoPerfil = (Label) nodo;
                    break;
                case "lblIdUsuarioPerfil":
                    lblIdUsuarioPerfil = (Label) nodo;
                    break;
                case "lblDireccion1":
                    lblDireccion1 = (Label) nodo;
                    break;
                case "lblDireccion2":
                    lblDireccion2 = (Label) nodo;
                    break;
                case "lblTotalEnvios":
                    lblTotalEnvios = (Label) nodo;
                    break;
                case "lblEnviosPendientes":
                    lblEnviosPendientes = (Label) nodo;
                    break;
                case "lblEnviosCompletados":
                    lblEnviosCompletados = (Label) nodo;
                    break;
            }
        }
        
        // Buscar recursivamente en los hijos
        if (nodo instanceof javafx.scene.Parent) {
            javafx.scene.Parent parent = (javafx.scene.Parent) nodo;
            for (Node child : parent.getChildrenUnmodifiable()) {
                buscarNodosRecursivo(child);
            }
        }
    }
    
    /**
     * Inicializa el controlador con la información del usuario logueado
     */
    public void inicializarUsuario(UsuarioDto usuario) {
        this.usuarioActual = usuario;
        // Actualizar header inmediatamente
        if (lblNombreUsuario != null) {
            lblNombreUsuario.setText(usuarioActual.nombre());
        }
        // Intentar actualizar el perfil, pero si la pestaña no está seleccionada, se actualizará cuando se seleccione
        Platform.runLater(() -> {
            // Resetear referencias a nodos para forzar búsqueda nueva
            lblNombrePerfil = null;
            lblCorreoPerfil = null;
            lblTelefonoPerfil = null;
            lblIdUsuarioPerfil = null;
            lblDireccion1 = null;
            lblDireccion2 = null;
            lblTotalEnvios = null;
            lblEnviosPendientes = null;
            lblEnviosCompletados = null;
            
            if (tabPane != null && "Perfil".equals(tabPane.getSelectionModel().getSelectedItem() != null ? 
                    tabPane.getSelectionModel().getSelectedItem().getText() : null)) {
                buscarNodosPerfil();
            }
            actualizarPerfil();
        });
    }
    
    private void actualizarPerfil() {
        if (usuarioActual == null) return;
        
        // Si los nodos del perfil no están disponibles, intentar buscarlos
        if (lblNombrePerfil == null) {
            buscarNodosPerfil();
        }
        
        // Actualizar información del perfil
        if (lblNombrePerfil != null) {
            lblNombrePerfil.setText("Nombre: " + usuarioActual.nombre());
        }
        if (lblCorreoPerfil != null) {
            lblCorreoPerfil.setText("Correo: " + usuarioActual.correo());
        }
        if (lblTelefonoPerfil != null) {
            lblTelefonoPerfil.setText("Teléfono: " + usuarioActual.telefono());
        }
        if (lblIdUsuarioPerfil != null) {
            lblIdUsuarioPerfil.setText("ID Usuario: " + usuarioActual.idUsuario());
        }
        
        // Actualizar direcciones
        if (usuarioActual.direcciones() != null && !usuarioActual.direcciones().isEmpty()) {
            if (lblDireccion1 != null && usuarioActual.direcciones().size() > 0) {
                String dir1 = usuarioActual.direcciones().get(0);
                lblDireccion1.setText("Dirección 1: " + (dir1 != null ? dir1 : ""));
            }
            if (lblDireccion2 != null && usuarioActual.direcciones().size() > 1) {
                String dir2 = usuarioActual.direcciones().get(1);
                lblDireccion2.setText("Dirección 2: " + (dir2 != null ? dir2 : ""));
            } else if (lblDireccion2 != null) {
                lblDireccion2.setText("Dirección 2: ");
            }
        } else {
            // Si no hay direcciones, mostrar mensaje vacío
            if (lblDireccion1 != null) {
                lblDireccion1.setText("Dirección 1: ");
            }
            if (lblDireccion2 != null) {
                lblDireccion2.setText("Dirección 2: ");
            }
        }
        
        // Actualizar estadísticas
        actualizarEstadisticas();
    }
    
    private void actualizarEstadisticas() {
        if (usuarioActual == null) return;
        
        // Guardar el ID del usuario para usarlo dentro del Platform.runLater
        String idUsuario = usuarioActual.idUsuario();
        
        // Actualizar los labels si están disponibles
        // Usar Platform.runLater para asegurar que estamos en el hilo de JavaFX
        Platform.runLater(() -> {
            // Buscar nodos si no están disponibles
            if (lblTotalEnvios == null || lblEnviosPendientes == null || lblEnviosCompletados == null) {
                buscarNodosPerfil();
            }
            
            // Calcular estadísticas dentro del Platform.runLater para asegurar que los datos estén actualizados
            var todosEnvios = model.listarEnvios();
            
            long totalEnvios = todosEnvios.stream()
                    .filter(e -> e.idUsuario() != null && e.idUsuario().equals(idUsuario))
                    .count();
            
            long enviosPendientes = todosEnvios.stream()
                    .filter(e -> e.idUsuario() != null && e.idUsuario().equals(idUsuario))
                    .filter(e -> e.estado() == estadoEnvio.SOLICITANDO || 
                                e.estado() == estadoEnvio.ASIGNADO || 
                                e.estado() == estadoEnvio.EN_RUTA)
                    .count();
            
            long enviosCompletados = todosEnvios.stream()
                    .filter(e -> e.idUsuario() != null && e.idUsuario().equals(idUsuario))
                    .filter(e -> e.estado() == estadoEnvio.ENTREGADO)
                    .count();
            
            // Actualizar los labels
            if (lblTotalEnvios != null) {
                lblTotalEnvios.setText(String.valueOf(totalEnvios));
            }
            if (lblEnviosPendientes != null) {
                lblEnviosPendientes.setText(String.valueOf(enviosPendientes));
            }
            if (lblEnviosCompletados != null) {
                lblEnviosCompletados.setText(String.valueOf(enviosCompletados));
            }
        });
    }

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
            if (usuarioActual == null) {
                mostrarAlerta("Error", "No hay usuario logueado");
                return;
            }
            
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            String desc = txtDescripcion.getText();

            Direccion origen = new Direccion("Calle 1 #2-3, Centro", "Armenia", "Frente al parque");
            Direccion destino = new Direccion("Carrera 10 #20-30, Norte", "Armenia", "Edificio azul");

            EnvioDto creado = usuarioController.crearEnvio(usuarioActual.idUsuario(), origen, destino, desc, peso, volumen, distancia, prioridad);
            println("Envío creado: id=" + creado.idEnvio() + ", costo=$" + (long) creado.costo());
            
            // Actualizar estadísticas después de crear el envío
            // Siempre intentar buscar los nodos y actualizar las estadísticas
            Platform.runLater(() -> {
                // Forzar búsqueda de nodos (resetear referencias primero)
                lblTotalEnvios = null;
                lblEnviosPendientes = null;
                lblEnviosCompletados = null;
                
                // Buscar nodos del perfil siempre (pueden estar en el FXML incluido aunque la pestaña no esté seleccionada)
                buscarNodosPerfil();
                // Actualizar estadísticas (se actualizarán los labels si están disponibles)
                actualizarEstadisticas();
            });
            
            // Limpiar campos
            txtPeso.clear();
            txtVolumen.clear();
            txtDistancia.clear();
            txtDescripcion.clear();
            chkPrioridad.setSelected(false);
        } catch (Exception e) {
            println("Error al crear envío: " + e.getMessage());
        }
    }

    @FXML
    protected void onListar() {
        if (txtSalida != null) {
            txtSalida.clear();
        }
        var lista = usuarioController.listarEnvios();
        if (lista.isEmpty()) {
            println("No hay envíos registrados.");
            // Actualizar estadísticas
            actualizarEstadisticas();
            return;
        }
        println("📦 Lista de Envíos:");
        println("===================");
        for (EnvioDto e : lista) {
            println("ID: " + e.idEnvio() + 
                   " | Estado: " + e.estado() + 
                   " | Costo: $" + (long) e.costo() +
                   " | Usuario: " + e.idUsuario());
        }
               println("===================");
               println("Total: " + lista.size() + " envío(s)");
               
               // Actualizar estadísticas
               Platform.runLater(() -> {
                   buscarNodosPerfil();
                   actualizarEstadisticas();
               });
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
               
               // Actualizar estadísticas después de cancelar
               if (ok) {
                   Platform.runLater(() -> {
                       buscarNodosPerfil();
                       actualizarEstadisticas();
                   });
                   txtIdCancelar.clear();
               }
    }

    private void println(String s) {
        txtSalida.appendText(s + "\n");
    }

    @FXML
    protected void onCerrarSesion() {
        try {
            // Obtener el Stage desde cualquier componente FXML disponible
            Stage stage = null;
            if (btnCerrarSesion != null && btnCerrarSesion.getScene() != null) {
                stage = (Stage) btnCerrarSesion.getScene().getWindow();
            } else if (txtSalida != null && txtSalida.getScene() != null) {
                stage = (Stage) txtSalida.getScene().getWindow();
            } else {
                javafx.scene.Node node = btnCerrarSesion != null ? btnCerrarSesion : txtSalida;
                if (node != null && node.getScene() != null) {
                    stage = (Stage) node.getScene().getWindow();
                }
            }
            
            if (stage == null) {
                mostrarAlerta("Error", "No se pudo obtener la ventana actual.");
                return;
            }
            
            // Cargar la vista de login
            java.net.URL url = getClass().getResource(
                    "/co/edu/uniquindio/citycourier/citycourier/Login.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró el archivo Login.fxml");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login - CityCourier");
            stage.centerOnScreen();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de login: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    protected void onSolicitarPedido() {
        // Cambiar a la pestaña de crear envío
        if (tabPane != null) {
            tabPane.getSelectionModel().select(0); // Pestaña "Crear Envío"
        }
    }

    @FXML
    protected void onVisualizarHistorial() {
        // Cambiar a la pestaña de mis envíos y cargar la lista
        if (tabPane != null) {
            tabPane.getSelectionModel().select(1); // Pestaña "Mis Envíos"
            onListar(); // Cargar automáticamente la lista
        }
    }

    @FXML
    protected void onGestionDireccion() {
        // Cambiar a la pestaña de gestionar dirección
        if (tabPane != null) {
            tabPane.getSelectionModel().select(3); // Pestaña "Gestionar Dirección"
        }
    }

    @FXML
    protected void onCrearPaquete() {
        // Similar a solicitar pedido, cambiar a la pestaña de crear envío
        if (tabPane != null) {
            tabPane.getSelectionModel().select(0); // Pestaña "Crear Envío"
        }
    }

    @FXML
    protected void onRastrearPedido() {
        // Cambiar a la pestaña de rastrear pedido
        if (tabPane != null) {
            tabPane.getSelectionModel().select(2); // Pestaña "Rastrear Pedido"
        }
        
        // Si ya hay un ID en el campo, buscar automáticamente
        String idEnvio = txtIdRastrear != null ? txtIdRastrear.getText() : null;
        if (idEnvio != null && !idEnvio.isBlank()) {
            rastrearEnvio(idEnvio);
        }
    }

    private void rastrearEnvio(String idEnvio) {
        var lista = usuarioController.listarEnvios();
        EnvioDto envioEncontrado = lista.stream()
                .filter(e -> e.idEnvio().equals(idEnvio))
                .findFirst()
                .orElse(null);
        
        if (envioEncontrado != null) {
            if (txtInfoRastreo != null) {
                txtInfoRastreo.clear();
                txtInfoRastreo.appendText("═══════════════════════════════════════\n");
                txtInfoRastreo.appendText("    INFORMACIÓN DEL ENVÍO\n");
                txtInfoRastreo.appendText("═══════════════════════════════════════\n\n");
                txtInfoRastreo.appendText("ID del Envío: " + envioEncontrado.idEnvio() + "\n");
                txtInfoRastreo.appendText("Estado: " + envioEncontrado.estado() + "\n");
                txtInfoRastreo.appendText("Costo: $" + (long)envioEncontrado.costo() + "\n\n");
                txtInfoRastreo.appendText("Dirección de Origen:\n");
                txtInfoRastreo.appendText("  " + envioEncontrado.direccionOrigen() + "\n\n");
                txtInfoRastreo.appendText("Dirección de Destino:\n");
                txtInfoRastreo.appendText("  " + envioEncontrado.direccionDestino() + "\n\n");
                txtInfoRastreo.appendText("Descripción: " + envioEncontrado.descripcion() + "\n");
                txtInfoRastreo.appendText("Peso: " + envioEncontrado.peso() + " kg\n");
                txtInfoRastreo.appendText("Volumen: " + envioEncontrado.volumen() + " m³\n");
                txtInfoRastreo.appendText("Fecha de Entrega: " + envioEncontrado.fechaEntrega() + "\n");
                txtInfoRastreo.appendText("═══════════════════════════════════════\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información del Envío");
                alert.setHeaderText("Estado del Envío: " + envioEncontrado.estado());
                alert.setContentText(
                    "ID: " + envioEncontrado.idEnvio() + "\n" +
                    "Estado: " + envioEncontrado.estado() + "\n" +
                    "Costo: $" + (long)envioEncontrado.costo() + "\n" +
                    "Origen: " + envioEncontrado.direccionOrigen() + "\n" +
                    "Destino: " + envioEncontrado.direccionDestino() + "\n" +
                    "Descripción: " + envioEncontrado.descripcion()
                );
                alert.showAndWait();
            }
        } else {
            if (txtInfoRastreo != null) {
                txtInfoRastreo.clear();
                txtInfoRastreo.appendText("❌ No se encontró un envío con el ID: " + idEnvio);
            } else {
                mostrarAlerta("Error", "No se encontró un envío con el ID: " + idEnvio);
            }
        }
    }
    
    @FXML
    protected void onAgregarDireccion() {
        String calle = txtCalle != null ? txtCalle.getText() : "";
        String ciudad = txtCiudad != null ? txtCiudad.getText() : "";
        String referencia = txtReferencia != null ? txtReferencia.getText() : "";
        
        if (calle.isBlank() || ciudad.isBlank()) {
            mostrarAlerta("Error", "Por favor complete al menos la calle y la ciudad.");
            return;
        }
        
        String direccionCompleta = calle + ", " + ciudad + (referencia.isBlank() ? "" : " - " + referencia);
        direcciones.add(direccionCompleta);
        
        if (txtCalle != null) txtCalle.clear();
        if (txtCiudad != null) txtCiudad.clear();
        if (txtReferencia != null) txtReferencia.clear();
        
        mostrarAlerta("Éxito", "Dirección agregada correctamente", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    protected void onEliminarDireccion() {
        String seleccionada = listaDirecciones != null ? listaDirecciones.getSelectionModel().getSelectedItem() : null;
        if (seleccionada == null) {
            mostrarAlerta("Error", "Por favor seleccione una dirección para eliminar.");
            return;
        }
        
        direcciones.remove(seleccionada);
        mostrarAlerta("Éxito", "Dirección eliminada correctamente", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    protected void onListarDirecciones() {
        // Por ahora muestra direcciones de ejemplo, se puede conectar con el modelo después
        if (listaDirecciones != null) {
            listaDirecciones.setItems(direcciones);
        }
    }

    @FXML
    protected void onRegresar() {
        // Regresar al login (similar a cerrar sesión)
        onCerrarSesion();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}