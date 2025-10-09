package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.*;

import java.util.*;

public class EnvioController {
    private static final Map<String, Shipment> envios = new HashMap<>();

    public double cotizar(double pesoKg, double volumenM3, Priority prioridad, EnumSet<AdditionalService> servicios) {
        Tariff t = TarifaController.obtener();
        double costo = t.getBase() + t.getRecargoPeso() * Math.max(0, pesoKg) + t.getRecargoVolumen() * Math.max(0, volumenM3);
        if (prioridad == Priority.PRIORITARIA) costo += t.getRecargoPrioridad();
        if (servicios.contains(AdditionalService.SEGURO)) costo += 2000;
        if (servicios.contains(AdditionalService.FRAGIL)) costo += 1000;
        if (servicios.contains(AdditionalService.FIRMA_REQUERIDA)) costo += 500;
        return Math.max(0, costo);
    }

    public Shipment crearEnvio(Address origen, Address destino, double peso, double volumen, Priority prioridad, EnumSet<AdditionalService> servicios) {
        String id = "E" + (100 + new Random().nextInt(900));
        Shipment s = new Shipment(id, origen, destino, peso, volumen, prioridad);
        s.setIdUsuario(AuthController.getCurrentUserId());
        s.setServicios(servicios);
        s.setCosto(cotizar(peso, volumen, prioridad, servicios));
        envios.put(id, s);
        return s;
    }

    public static Optional<Shipment> obtener(String id) { return Optional.ofNullable(envios.get(id)); }
    public static Collection<Shipment> listarTodos() { return envios.values(); }
    public static List<Shipment> listarPorUsuario(String idUsuario) {
        List<Shipment> list = new ArrayList<>();
        for (Shipment s : envios.values()) {
            if (idUsuario != null && idUsuario.equals(s.getIdUsuario())) list.add(s);
        }
        return list;
    }
}
