package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.Envio;
import co.edu.uniquindio.citycourier.citycourier.service.envio.EnvioService;
import co.edu.uniquindio.citycourier.citycourier.service.envio.EnvioServiceMem;
import co.edu.uniquindio.citycourier.citycourier.service.tarifa.TarifaService;
import co.edu.uniquindio.citycourier.citycourier.service.tarifa.TarifaServiceSimple;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioController {
    private final EnvioService envioService = new EnvioServiceMem();
    private TarifaService tarifaService = new TarifaServiceSimple();

    public double cotizar (double peso, double volumen, double distanciaKm, boolean prioridad) {
        return tarifaService.cotizar(peso, volumen, distanciaKm, prioridad);
    }
    public Envio crearEnvio(String idUsuario, Direccion origen, Direccion destino, String descripcion, double peso, double volumen, double distanvia, boolean prioridad){
        double costo = cotizar(peso, volumen, distanvia, prioridad);
       Envio e = new Envio(null,origen,destino,descripcion,peso,volumen,costo, LocalDateTime.now().plusHours(4),idUsuario);
         return envioService.crear(e);
    }
    public List<Envio> listarEnvios(){
        return envioService.listar();
    }
    public boolean cancelarEnvio (String idEnvio){
        return envioService.cancelar(idEnvio);
    }
}
