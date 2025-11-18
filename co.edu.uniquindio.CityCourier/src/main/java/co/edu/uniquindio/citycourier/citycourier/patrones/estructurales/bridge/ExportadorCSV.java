package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ExportadorCSV implements Exportador {
    @Override
    public void exportar(String titulo, List<String[]> contenido, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(nombreArchivo + ".csv"), StandardCharsets.UTF_8))) {

            writer.println(titulo);
            writer.println("Fecha Reporte: " + LocalDate.now());
            writer.println();

            for (String[] fila : contenido) {
                writer.println(String.join(",", fila));
            }

            writer.flush();
            System.out.println("CSV exportado correctamente: " + nombreArchivo + ".csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
