package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.Shipment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteController {
    public Path exportarEnviosCSV(Path destino) throws IOException {
        List<String> lines = EnvioController.listarTodos().stream()
                .sorted(Comparator.comparing(Shipment::getFechaCreacion))
                .map(s -> String.join(",",
                        s.getIdEnvio(),
                        s.getOrigen().toString(),
                        s.getDestino().toString(),
                        s.getEstado().name(),
                        String.valueOf((long) s.getCosto())
                ))
                .collect(Collectors.toList());
        lines.add(0, "idEnvio,origen,destino,estado,costo");
        Files.createDirectories(destino.getParent());
        Files.write(destino, lines);
        return destino;
    }
}
