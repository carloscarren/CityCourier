package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.data.DataStore;
import co.edu.uniquindio.citycourier.citycourier.domain.Tariff;

public class TarifaController {
    public Tariff obtener() { return DataStore.getInstance().getTarifaActual(); }
    public void actualizar(Tariff t) { DataStore.getInstance().setTarifaActual(t); }
}
