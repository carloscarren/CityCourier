package co.edu.uniquindio.citycourier.citycourier.utils;

import co.edu.uniquindio.citycourier.citycourier.model.*;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad que genera datos de ejemplo para inicializar el sistema CityCourier.
 * Incluye usuarios, repartidores, envíos y pagos de prueba.
 */
public class DataUtil {

    // ============================================================
    // ====================== USUARIOS ============================
    // ============================================================

    public static List<Usuario> crearUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        Usuario usuario1 = new Usuario("U001", "Carlos", "Pérez", "carlos@mail.com",
                "3214567890", "1234", tipoUsuario.CLIENTE);

        Usuario usuario2 = new Usuario("U002", "Ana", "Gómez", "ana@mail.com",
                "3123456789", "1234", tipoUsuario.CLIENTE);

        // Usuario administrador para pruebas
        Usuario admin = new Usuario("ADM001", "Admin", "Sistema", "admin@citycourier.com",
                "3001234567", "admin123", tipoUsuario.ADMINISTRADOR);

        // Agregar direcciones frecuentes
        usuario1.agregarDireccionFrecuente(
                new Direccion("D001", "Calle 1 # 2-3", "Quindío", "Centro - Frente al parque")
        );
        usuario2.agregarDireccionFrecuente(
                new Direccion("D002", "Calle 5 # 10-20", "Quindío", "Norte - Junto a la biblioteca")
        );

        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(admin);

        return usuarios;
    }

    // ============================================================
    // ====================== REPARTIDORES ========================
    // ============================================================

    public static List<Repartidor> crearRepartidores() {
        List<Repartidor> repartidores = new ArrayList<>();

        List<String> enviosR1 = new ArrayList<>();
        List<String> enviosR2 = new ArrayList<>();

        // Constructor completo: (id, nombre, documento, telefono, vehiculo, zona, listaEnvios)
        Repartidor repartidor1 = new Repartidor("R001", "Luis Herrera", "10901122",
                "3174569870", "Moto", "Centro", enviosR1);

        Repartidor repartidor2 = new Repartidor("R002", "María López", "10903344",
                "3189876543", "Bici", "Norte", enviosR2);

        repartidores.add(repartidor1);
        repartidores.add(repartidor2);

        return repartidores;
    }

    // ============================================================
    // ======================== ENVÍOS ============================
    // ============================================================

    public static List<Envio> crearEnvios(List<Usuario> usuarios) {
        List<Envio> envios = new ArrayList<>();

        if (usuarios == null || usuarios.isEmpty()) return envios;

        Direccion origen1 = new Direccion("DO1", "Calle 2 # 3-4", "Quindío", "Centro - Cerca del banco");
        Direccion destino1 = new Direccion("DD1", "Calle 8 # 15-10", "Quindío", "Sur - Frente al colegio");

        Direccion origen2 = new Direccion("DO2", "Calle 10 # 20-15", "Quindío", "Norte - Cerca del hospital");
        Direccion destino2 = new Direccion("DD2", "Carrera 5 # 7-9", "Quindío", "Oeste - Frente al parqueadero");

        Envio envio1 = new Envio(
                "E001",
                origen1,
                destino1,
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
                origen2,
                destino2,
                "Paquete mediano",
                5.0,
                0.8,
                25000,
                metodoPago.EFECTIVO,
                LocalDateTime.now().plusDays(2),
                usuarios.get(1).getIdUsuario()
        );

        envios.add(envio1);
        envios.add(envio2);

        return envios;
    }

    // ============================================================
    // ======================== PAGOS =============================
    // ============================================================

    public static List<Pago> crearPagos(List<Envio> envios) {
        List<Pago> pagos = new ArrayList<>();

        if (envios == null || envios.isEmpty()) return pagos;

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

    // ============================================================
    // =========== ASIGNACIÓN DE ENVÍOS A REPARTIDORES ============
    // ============================================================

    public static void asignarEnviosARepartidores(List<Envio> envios, List<Repartidor> repartidores) {
        if (envios == null || repartidores == null || envios.isEmpty() || repartidores.isEmpty()) return;

        int cantidad = Math.min(envios.size(), repartidores.size());
        for (int i = 0; i < cantidad; i++) {
            Repartidor rep = repartidores.get(i);
            Envio env = envios.get(i);
            rep.asignarEnvio(env.getIdEnvio());
            env.asignarRepartidor(rep.getIdRepartidor());
        }
    }
}

