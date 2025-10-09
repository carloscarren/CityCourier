package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.*;

import java.util.EnumSet;
import java.util.Random;

public class EnvioController {
    public double cotizar(double pesoKg, double volumenM3, Priority prioridad, EnumSet<AdditionalService> servicios) {
        Tariff t = DataStore.getInstance().getTarifaActual();
        double costo = t.getBase() + t.getRecargoPeso() * Math.max(0, pesoKg) + t.getRecargoVolumen() * Math.max(0, volumenM3);
        if (prioridad == Priority.PRIORITARIA) costo += t.getRecargoPrioridad();
        if (servicios.contains(AdditionalService.SEGURO)) costo += 2000;
        if (servicios.contains(AdditionalService.FRAGIL)) costo += 1000;
        if (servicios.contains(AdditionalService.FIRMA_REQUERIDA)) costo += 500;
        return Math.max(0, costo);
    }

    public Shipment crearEnvio(Address origen, Address destino, double peso, double volumen, Priority prioridad, EnumSet<AdditionalService> servicios) {
        DataStore ds = DataStore.getInstance();
        String id = "E" + (100 + new Random().nextInt(900));
        Shipment s = new Shipment(id, origen, destino, peso, volumen, prioridad);
        s.setIdUsuario(ds.getCurrentUserId());
        s.setServicios(servicios);
        s.setCosto(cotizar(peso, volumen, prioridad, servicios));
        ds.getEnvios().put(id, s);
        return s;
    }
}
