package co.edu.uniquindio.citycourier.citycourier.viewController.administrador;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

public class ViewControllerMetricas {

    @FXML
    private PieChart pieChartEnvios;

    @FXML
    private BarChart<String, Number> barChartEnvios;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final ModelCityCourier model = ModelCityCourier.getInstance();

    @FXML
    public void initialize() {
        cargarDatosEnGraficas();
    }

    private void cargarDatosEnGraficas() {
        // Obtener datos de envíos por estado
        Map<estadoEnvio, Integer> enviosPorEstado = contarEnviosPorEstado();

        // Configurar PieChart
        if (pieChartEnvios != null) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<estadoEnvio, Integer> entry : enviosPorEstado.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey().name(), entry.getValue()));
            }
            pieChartEnvios.setData(pieChartData);
            pieChartEnvios.setTitle("Envíos por Estado");
        }

        // Configurar BarChart
        if (barChartEnvios != null && xAxis != null && yAxis != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Cantidad de Envíos");
            
            for (Map.Entry<estadoEnvio, Integer> entry : enviosPorEstado.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey().name(), entry.getValue()));
            }
            
            barChartEnvios.getData().clear();
            barChartEnvios.getData().add(series);
            barChartEnvios.setTitle("Distribución de Envíos por Estado");
            
            xAxis.setLabel("Estado");
            yAxis.setLabel("Cantidad");
        }
    }

    private Map<estadoEnvio, Integer> contarEnviosPorEstado() {
        Map<estadoEnvio, Integer> conteo = new HashMap<>();
        
        // Inicializar todos los estados con 0
        for (estadoEnvio estado : estadoEnvio.values()) {
            conteo.put(estado, 0);
        }
        
        // Contar envíos por estado
        for (EnvioDto envio : model.listarEnvios()) {
            estadoEnvio estado = envio.estado();
            conteo.put(estado, conteo.get(estado) + 1);
        }
        
        return conteo;
    }
}

