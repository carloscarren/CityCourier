package co.edu.uniquindio.citycourier.citycourier.utils;

import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<Usuario> crearUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        Usuario usuario1 = new Usuario("U001", "Carlos Pérez", "carlos@mail.com", "3214567890", "1234", tipoUsuario.CLIENTE);
        Usuario usuario2 = new Usuario("U002", "Ana Gómez", "ana@mail.com", "3123456789", "1234", tipoUsuario.CLIENTE);

        // Agregar direcciones frecuentes
        usuario1.agregarDireccionFrecuente(new Direccion("D001", "Centro", "Calle 1 # 2-3", "Quindío", "Frente al parque"));
        usuario2.agregarDireccionFrecuente(new Direccion("D002", "Norte", "Calle 5 # 10-20", "Quindío", "Junto a la biblioteca"));

        usuarios.add(usuario1);
        usuarios.add(usuario2);

        return usuarios;
    }

    public static List<Repartidor> crearRepartidores() {
        List<Repartidor> repartidores = new ArrayList<>();

        List<String> enviosR1 = new ArrayList<>();
        List<String> enviosR2 = new ArrayList<>();

        Repartidor repartidor1 = new Repartidor("R001", "Luis Herrera", "3174569870", "Moto", "Centro", enviosR1);
        Repartidor repartidor2 = new Repartidor("R002", "María López", "3189876543", "Bici", "Norte", enviosR2);

        repartidores.add(repartidor1);
        repartidores.add(repartidor2);

        return repartidores;
    }

    public static List<Envio> crearEnvios(List<Usuario> usuarios) {
        List<Envio> envios = new ArrayList<>();

        Direccion origen = new Direccion("D003", "Centro", "Calle 2 # 3-4", "Quindío", "Cerca del banco");
        Direccion destino = new Direccion("D004", "Sur", "Calle 8 # 15-10", "Quindío", "Frente al colegio");

        Envio envio1 = new Envio(
                "E001",
                origen,
                destino,
                "Paquete pequeño",
                2.5,
                0.3,
                15000,
                metodoPago.TARJETA_CREDITO,
                LocalDateTime.now().plusDays(1),
                usuarios.get(0).getIdUsuario()
        );

        Envio envio2 = new Envio(
                "E002",
                origen,
                destino,
                "Paquete mediano",
                5.0,
                0.8,
                25000,
                metodoPago.PAYPAL,
                LocalDateTime.now().plusDays(2),
                usuarios.get(1).getIdUsuario()
        );

        envios.add(envio1);
        envios.add(envio2);

        return envios;
    }

    public static List<Pago> crearPagos(List<Envio> envios) {
        List<Pago> pagos = new ArrayList<>();

        Pago pago1 = new Pago(
                "P001",
                envios.get(0).getIdEnvio(),
                envios.get(0).getCosto(),
                envios.get(0).getMetodoPago().toString(),
                "TXN123456"
        );
        Pago pago2 = new Pago(
                "P002",
                envios.get(1).getIdEnvio(),
                envios.get(1).getCosto(),
                envios.get(1).getMetodoPago().toString(),
                "TXN654321"
        );

        pagos.add(pago1);
        pagos.add(pago2);

        return pagos;
    }

    public static void asignarEnviosARepartidores(List<Envio> envios, List<Repartidor> repartidores) {
        if (!envios.isEmpty() && repartidores.size() >= 2) {
            repartidores.get(0).asignarEnvio(envios.get(0).getIdEnvio());
            envios.get(0).asignarRepartidor(repartidores.get(0).getIdRepartidor());

            repartidores.get(1).asignarEnvio(envios.get(1).getIdEnvio());
            envios.get(1).asignarRepartidor(repartidores.get(1).getIdRepartidor());
        }
    }
}
