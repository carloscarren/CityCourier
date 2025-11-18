package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.bridge;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class ExportadorPDF implements Exportador {

    private Font cargarArial(float tamano, int estilo) {
        try {
            String rutaArial = System.getenv("SystemRoot") + "/Fonts/arial.ttf"; // Windows
            BaseFont baseFont = BaseFont.createFont(rutaArial, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, tamano, estilo);
        } catch (Exception e) {
            // Fallback
            System.out.println("⚠️ No se pudo cargar Arial, usando Helvetica");
            return FontFactory.getFont(FontFactory.HELVETICA, tamano, estilo);
        }
    }

    @Override
    public void exportar(String titulo, List<String[]> contenido, String nombreArchivo) {
        Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo + ".pdf"));
            documento.open();

            Font fontTitulo = cargarArial(14, Font.BOLD);
            Font fontNormal = cargarArial(12, Font.NORMAL);
            Font fontBold = cargarArial(12, Font.BOLD);

            // Título
            Paragraph parrafoTitulo = new Paragraph(titulo, fontTitulo);
            parrafoTitulo.setAlignment(Element.ALIGN_LEFT);
            documento.add(parrafoTitulo);

            // Fecha
            Paragraph fecha = new Paragraph("Fecha Reporte: " + LocalDate.now(), fontNormal);
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(10);
            documento.add(fecha);

            documento.add(new LineSeparator());

            if (contenido == null || contenido.isEmpty()) {
                documento.add(new Paragraph("No hay datos para mostrar.", fontNormal));
                documento.close();
                return;
            }

            // Tabla info usuario
            PdfPTable tablaUsuario = new PdfPTable(2);
            tablaUsuario.setWidthPercentage(100);
            tablaUsuario.setSpacingBefore(10);

            int i = 0;
            for (; i < contenido.size(); i++) {
                String[] fila = contenido.get(i);
                if (fila.length == 1 && fila[0].isBlank()) {
                    i++;
                    break;
                }
                if (fila.length == 2) {
                    PdfPCell c1 = new PdfPCell(new Phrase(fila[0], fontNormal));
                    PdfPCell c2 = new PdfPCell(new Phrase(fila[1], fontNormal));
                    c1.setBorder(Rectangle.NO_BORDER);
                    c2.setBorder(Rectangle.NO_BORDER);
                    tablaUsuario.addCell(c1);
                    tablaUsuario.addCell(c2);
                }
            }
            documento.add(tablaUsuario);

            // Validar que queda contenido
            if (i >= contenido.size()) {
                documento.add(new Paragraph("No hay tabla de datos para mostrar.", fontNormal));
                documento.close();
                return;
            }

            String[] headers = contenido.get(i);
            PdfPTable tablaDatos = new PdfPTable(headers.length);
            tablaDatos.setWidthPercentage(100);
            tablaDatos.setSpacingBefore(15);

            // Encabezados
            for (String header : headers) {
                PdfPCell celda = new PdfPCell(new Phrase(header, fontBold));
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(6);
                tablaDatos.addCell(celda);
            }
            i++;

            // Datos
            for (; i < contenido.size(); i++) {
                for (String dato : contenido.get(i)) {
                    PdfPCell celda = new PdfPCell(new Phrase(dato, fontNormal));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setPadding(5);
                    tablaDatos.addCell(celda);
                }
            }

            documento.add(tablaDatos);
            documento.close();
            System.out.println("✅ PDF generado correctamente: " + nombreArchivo + ".pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
