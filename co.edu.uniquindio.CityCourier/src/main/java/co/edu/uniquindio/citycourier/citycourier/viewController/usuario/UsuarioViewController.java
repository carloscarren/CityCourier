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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    
    // Campos para crear envío
    @FXML
    private TextField txtCalleOrigen;
    @FXML
    private javafx.scene.control.ComboBox<String> cmbCiudadOrigen;
    @FXML
    private TextField txtReferenciaOrigen;
    @FXML
    private TextField txtCalleDestino;
    @FXML
    private javafx.scene.control.ComboBox<String> cmbCiudadDestino;
    @FXML
    private TextField txtReferenciaDestino;
    
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
    private javafx.scene.control.Button btnDescargarReporte;
    private javafx.scene.control.ComboBox<String> cmbFormatoReporte;
    
    private final ObservableList<String> direcciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicializar lista de direcciones
        if (listaDirecciones != null) {
            listaDirecciones.setItems(direcciones);
        }
        
        // Cargar ciudades en los ComboBoxes de origen y destino
        if (cmbCiudadOrigen != null && cmbCiudadDestino != null) {
            var ciudades = model.obtenerNombresCiudades();
            cmbCiudadOrigen.setItems(FXCollections.observableArrayList(ciudades));
            cmbCiudadDestino.setItems(FXCollections.observableArrayList(ciudades));
        }
        
        // Configurar ComboBox de formato de reporte
        if (cmbFormatoReporte != null) {
            cmbFormatoReporte.setItems(FXCollections.observableArrayList("PDF", "CSV"));
            cmbFormatoReporte.setValue("PDF"); // Valor por defecto
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
                        btnDescargarReporte = null;
                        
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
                
                // Buscar y configurar el botón de reporte
                Node btnNodo = escena.getRoot().lookup("#btnDescargarReporte");
                if (btnNodo != null && btnNodo instanceof javafx.scene.control.Button) {
                    btnDescargarReporte = (javafx.scene.control.Button) btnNodo;
                    btnDescargarReporte.setOnAction(e -> onGenerarReporte());
                }
                
                // Buscar el ComboBox de formato
                Node cmbNodo = escena.getRoot().lookup("#cmbFormatoReporte");
                if (cmbNodo != null && cmbNodo instanceof javafx.scene.control.ComboBox) {
                    @SuppressWarnings("unchecked")
                    javafx.scene.control.ComboBox<String> combo = (javafx.scene.control.ComboBox<String>) cmbNodo;
                    cmbFormatoReporte = combo;
                    // Asegurar que tenga las opciones y valor por defecto
                    if (combo.getItems().isEmpty()) {
                        combo.setItems(FXCollections.observableArrayList("PDF", "CSV"));
                        combo.setValue("PDF");
                    }
                }
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
                        // Configurar el botón de reporte si se encontró
                        if (btnDescargarReporte != null) {
                            btnDescargarReporte.setOnAction(e -> onGenerarReporte());
                        }
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
                case "btnDescargarReporte":
                    if (nodo instanceof javafx.scene.control.Button) {
                        btnDescargarReporte = (javafx.scene.control.Button) nodo;
                    }
                    break;
                case "cmbFormatoReporte":
                    if (nodo instanceof javafx.scene.control.ComboBox) {
                        @SuppressWarnings("unchecked")
                        javafx.scene.control.ComboBox<String> combo = (javafx.scene.control.ComboBox<String>) nodo;
                        cmbFormatoReporte = combo;
                    }
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
            
            // Validar campos obligatorios
            if (txtPeso.getText().isBlank() || txtVolumen.getText().isBlank() || 
                txtDistancia.getText().isBlank() || txtDescripcion.getText().isBlank()) {
                mostrarAlerta("Error", "Por favor complete todos los campos obligatorios.");
                return;
            }
            
            // Validar direcciones
            if (txtCalleOrigen.getText().isBlank() || cmbCiudadOrigen.getValue() == null) {
                mostrarAlerta("Error", "Por favor complete la dirección de origen (calle y ciudad).");
                return;
            }
            
            if (txtCalleDestino.getText().isBlank() || cmbCiudadDestino.getValue() == null) {
                mostrarAlerta("Error", "Por favor complete la dirección de destino (calle y ciudad).");
                return;
            }
            
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            boolean prioridad = chkPrioridad.isSelected();
            String desc = txtDescripcion.getText();

            // Crear direcciones desde los campos ingresados por el usuario
            Direccion origen = new Direccion(
                txtCalleOrigen.getText(),
                cmbCiudadOrigen.getValue(),
                txtReferenciaOrigen.getText().isBlank() ? null : txtReferenciaOrigen.getText()
            );
            
            Direccion destino = new Direccion(
                txtCalleDestino.getText(),
                cmbCiudadDestino.getValue(),
                txtReferenciaDestino.getText().isBlank() ? null : txtReferenciaDestino.getText()
            );

            EnvioDto creado = usuarioController.crearEnvio(usuarioActual.idUsuario(), origen, destino, desc, peso, volumen, distancia, prioridad);
            
            mostrarAlerta("Éxito", "Envío creado correctamente.\nID: " + creado.idEnvio() + "\nCosto: $" + (long) creado.costo());
            
            // Actualizar estadísticas después de crear el envío
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
            txtCalleOrigen.clear();
            txtReferenciaOrigen.clear();
            txtCalleDestino.clear();
            txtReferenciaDestino.clear();
            cmbCiudadOrigen.setValue(null);
            cmbCiudadDestino.setValue(null);
            chkPrioridad.setSelected(false);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos para peso, volumen y distancia.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear envío: " + e.getMessage());
            e.printStackTrace();
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

    /**
     * Genera un reporte de gastos del usuario actual en formato PDF.
     * Permite al usuario seleccionar dónde guardar el archivo.
     */
    @FXML
    protected void onGenerarReporte() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "No hay usuario autenticado.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Obtener el usuario como entidad del modelo
            co.edu.uniquindio.citycourier.citycourier.model.Usuario usuarioEntidad = 
                model.obtenerUsuarioEntidad(usuarioActual.idUsuario());

            if (usuarioEntidad == null) {
                mostrarAlerta("Error", "No se pudo encontrar la información del usuario.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener el formato seleccionado (PDF por defecto)
            String formato = "PDF";
            if (cmbFormatoReporte != null && cmbFormatoReporte.getValue() != null) {
                formato = cmbFormatoReporte.getValue();
            } else {
                // Si el ComboBox no está disponible, intentar buscarlo dinámicamente
                if (tabPane != null && tabPane.getScene() != null) {
                    javafx.scene.Node nodoFormato = tabPane.getScene().getRoot().lookup("#cmbFormatoReporte");
                    if (nodoFormato != null && nodoFormato instanceof javafx.scene.control.ComboBox) {
                        @SuppressWarnings("unchecked")
                        javafx.scene.control.ComboBox<String> combo = (javafx.scene.control.ComboBox<String>) nodoFormato;
                        if (combo.getValue() != null) {
                            formato = combo.getValue();
                        }
                    }
                }
            }
            
            // Abrir FileChooser para seleccionar dónde guardar el archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte de Gastos");
            fileChooser.setInitialFileName("reporte_gastos_" + usuarioActual.idUsuario());
            
            // Configurar filtros según el formato seleccionado
            if (formato.equalsIgnoreCase("CSV")) {
                FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter(
                    "Archivos CSV (*.csv)", "*.csv");
                fileChooser.getExtensionFilters().add(extFilterCSV);
            } else {
                FileChooser.ExtensionFilter extFilterPDF = new FileChooser.ExtensionFilter(
                    "Archivos PDF (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilterPDF);
            }

            // Obtener el Stage desde cualquier componente disponible
            Stage stage = null;
            if (tabPane != null && tabPane.getScene() != null) {
                stage = (Stage) tabPane.getScene().getWindow();
            } else if (txtSalida != null && txtSalida.getScene() != null) {
                stage = (Stage) txtSalida.getScene().getWindow();
            }

            if (stage == null) {
                mostrarAlerta("Error", "No se pudo obtener la ventana actual.", Alert.AlertType.ERROR);
                return;
            }

            File archivoSeleccionado = fileChooser.showSaveDialog(stage);
            
            if (archivoSeleccionado == null) {
                // Usuario canceló la operación
                return;
            }
            
            // Obtener la ruta del archivo (sin extensión, ya que el exportador la agrega)
            String rutaArchivo = archivoSeleccionado.getAbsolutePath();
            String extension = formato.equalsIgnoreCase("CSV") ? ".csv" : ".pdf";
            if (rutaArchivo.endsWith(extension)) {
                rutaArchivo = rutaArchivo.substring(0, rutaArchivo.length() - extension.length());
            } else if (rutaArchivo.endsWith(".pdf") && formato.equalsIgnoreCase("CSV")) {
                rutaArchivo = rutaArchivo.substring(0, rutaArchivo.length() - 4);
            } else if (rutaArchivo.endsWith(".csv") && formato.equalsIgnoreCase("PDF")) {
                rutaArchivo = rutaArchivo.substring(0, rutaArchivo.length() - 4);
            }

            // Instanciar ReporteService y el exportador según el formato seleccionado (Patrón Bridge)
            co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ReporteService reporteService = 
                new co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ReporteService();
            
            co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.Exportador exportador;
            if (formato.equalsIgnoreCase("CSV")) {
                exportador = new co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ExportadorCSV();
            } else {
                exportador = new co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge.ExportadorPDF();
            }

            // Generar el reporte usando el patrón Bridge
            reporteService.generarReporteGastosUsuario(usuarioEntidad, rutaArchivo, exportador);

            mostrarAlerta("Éxito", 
                "Reporte de gastos generado correctamente en:\n" + rutaArchivo + extension, 
                Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", 
                "Error al generar el reporte: " + e.getMessage(), 
                Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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