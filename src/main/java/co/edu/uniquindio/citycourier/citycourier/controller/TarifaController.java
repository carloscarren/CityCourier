package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.domain.Tariff;

public class TarifaController {
    private static Tariff tarifa = new Tariff(5000, 800, 600, 3000);
    public static Tariff obtener() { return tarifa; }
    public static void actualizar(Tariff t) { tarifa = t; }
}
