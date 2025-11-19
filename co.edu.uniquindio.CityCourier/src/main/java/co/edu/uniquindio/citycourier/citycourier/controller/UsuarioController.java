package co.edu.uniquindio.citycourier.citycourier.controller;

import co.edu.uniquindio.citycourier.citycourier.factory.ModelCityCourier;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.EnvioDto;
import co.edu.uniquindio.citycourier.citycourier.model.Direccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoEnvio;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoTransaccion;
import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.TipoCuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Cuenta;
import co.edu.uniquindio.citycourier.citycourier.model.Usuario;
import co.edu.uniquindio.citycourier.citycourier.model.Transaccion;
import co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.singleton.ModelBilleteraVirtual;
import co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod.FabricaTransacciones;
import co.edu.uniquindio.citycourier.citycourier.patrones.creacionales.factoryMethod.DatosTransaccion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador que gestiona las operaciones de los usuarios,
 * como crear envíos, listar envíos y cancelarlos.
 * Actúa como intermediario entre la vista y la lógica de negocio (ModelCityCourier).
 */
public class UsuarioController {

    // Instancia del modelo principal (Singleton)
    private final ModelCityCourier factory = ModelCityCourier.getInstance();

    /**
     * Crea un nuevo envío y lo registra en el sistema.
     * También registra la transacción financiera de retiro en ModelBilleteraVirtual.
     */
    public EnvioDto crearEnvio(String idUsuario, Direccion origen, Direccion destino,
                               String descripcion, double peso, double volumen,
                               double distancia, boolean prioridad) {

        // Cálculo de tarifa simulada
        double costo = cotizar(peso, volumen, distancia, prioridad);

        // Generar ID único para el envío
        String idEnvio = "E" + System.currentTimeMillis();

        // Construcción del DTO del envío
        EnvioDto envio = new EnvioDto(
                idEnvio,
                origen.toString(),
                destino.toString(),
                descripcion,
                peso,
                volumen,
                costo,
                LocalDateTime.now().plusHours(4).toString(),
                idUsuario,
                estadoEnvio.SOLICITANDO
        );

        // Registrar el envío en el sistema de logística
        factory.crearEnvio(envio);
        
        // ============================================================
        // INTEGRACIÓN CON MÓDULO FINANCIERO
        // Registrar la transacción de retiro en ModelBilleteraVirtual
        // ============================================================
        try {
            ModelBilleteraVirtual modelFinanciero = ModelBilleteraVirtual.getInstancia();
            
            // Obtener el usuario completo desde ModelCityCourier
            Usuario usuarioCompleto = factory.obtenerUsuarioEntidad(idUsuario);
            
            if (usuarioCompleto != null) {
                // Buscar la cuenta principal del usuario
                Cuenta cuentaPrincipal = obtenerOCrearCuentaPrincipal(usuarioCompleto, modelFinanciero);
                
                if (cuentaPrincipal != null) {
                    // Crear la transacción de retiro usando el patrón Factory Method
                    String idTransaccion = "TXN" + System.currentTimeMillis();
                    DatosTransaccion datosTransaccion = new DatosTransaccion(
                            idTransaccion,
                            cuentaPrincipal,
                            LocalDate.now(),
                            costo,
                            "Pago de envío: " + idEnvio,
                            null, // No hay cuenta destino en un retiro
                            TipoTransaccion.RETIRO,
                            null  // Sin presupuesto por ahora
                    );
                    
                    // Usar la fábrica para crear la transacción
                    Transaccion transaccion = FabricaTransacciones.crear(datosTransaccion);
                    
                    // Agregar la transacción a la cuenta
                    cuentaPrincipal.getListaTransacciones().add(transaccion);
                    
                    // Registrar la transacción en el modelo financiero
                    modelFinanciero.agregarTransaccion(transaccion);
                }
            }
        } catch (Exception e) {
            // Si hay un error al registrar la transacción, no fallar la creación del envío
            // pero registrar el error para debugging
            System.err.println("Error al registrar transacción financiera para el envío " + idEnvio + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return envio;
    }
    
    /**
     * Obtiene la cuenta principal del usuario o crea una por defecto si no existe.
     * 
     * @param usuario Usuario para el cual buscar o crear la cuenta
     * @param modelFinanciero Instancia de ModelBilleteraVirtual
     * @return Cuenta principal del usuario
     */
    private Cuenta obtenerOCrearCuentaPrincipal(Usuario usuario, ModelBilleteraVirtual modelFinanciero) {
        // Buscar cuentas existentes del usuario
        List<Cuenta> cuentasUsuario = modelFinanciero.obtenerCuentasPorUsuario(usuario.getIdUsuario());
        
        // Si el usuario ya tiene una cuenta, usar la primera (cuenta principal)
        if (!cuentasUsuario.isEmpty()) {
            return cuentasUsuario.get(0);
        }
        
        // Si no tiene cuenta, crear una por defecto
        String idCuenta = "CUENTA_" + usuario.getIdUsuario() + "_" + System.currentTimeMillis();
        Cuenta cuentaNueva = new Cuenta(
                idCuenta,
                "CityCourier",
                "CC-" + usuario.getIdUsuario(),
                TipoCuenta.AHORROS,
                usuario,
                null // Sin administrador asociado
        );
        
        // Agregar la cuenta al modelo financiero
        modelFinanciero.agregarCuenta(cuentaNueva);
        
        // Agregar la cuenta al usuario
        if (usuario.getListaCuentas() != null) {
            usuario.getListaCuentas().add(cuentaNueva);
        }
        
        return cuentaNueva;
    }

    /**
     * Retorna la lista de todos los envíos del sistema.
     */
    public List<EnvioDto> listarEnvios() {
        return factory.listarEnvios();
    }

    /**
     * Cancela un envío si aún no ha sido asignado.
     */
    public boolean cancelarEnvio(String idEnvio) {
        return factory.cancelarEnvio(idEnvio);
    }

    /**
     * Calcula una cotización estimada según peso, volumen y distancia.
     * Utiliza el modelo Tarifa para calcular el costo real.
     */
    public double cotizar(double peso, double volumen, double distancia, boolean prioridad) {
        // Crear una tarifa estándar para el cálculo
        co.edu.uniquindio.citycourier.citycourier.model.Tarifa tarifa = 
            new co.edu.uniquindio.citycourier.citycourier.model.Tarifa(
                "TARIFA_STD",
                5000,  // costoBase
                1000,  // costoPorPeso
                800,   // costoPorVolumen
                500,   // costoPorDistancia
                2000,  // recargoPrioridad
                0,     // recargoSeguro
                0,     // recargoFragil
                0,     // recargoFirma
                "GENERAL" // zona
            );

        // Usar el método calcularCosto del modelo Tarifa
        return tarifa.calcularCosto(peso, volumen, distancia, prioridad, false, false, false);
    }
}
