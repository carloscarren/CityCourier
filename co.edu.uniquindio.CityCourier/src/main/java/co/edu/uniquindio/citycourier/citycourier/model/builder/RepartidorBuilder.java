package co.edu.uniquindio.citycourier.citycourier.model.builder;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.estadoRepartidor;
import co.edu.uniquindio.citycourier.citycourier.model.Repartidor;

import java.util.ArrayList;
import java.util.List;


public class RepartidorBuilder {
        private String idRepartidor;
        private String nombreCompleto;
        private String documento;
        private String telefono;
        private String vehiculoAsignado;
        private estadoRepartidor estado = estadoRepartidor.INACTIVO;
        private String zonaCobertura;
        private List<String> enviosAsignados = new ArrayList<>();

        public RepartidorBuilder setIdRepartidor(String idRepartidor) {
            this.idRepartidor = idRepartidor;
            return this;
        }

        public RepartidorBuilder setNombreCompleto(String nombre) {
            this.nombreCompleto = nombre;
            return this;
        }

        public RepartidorBuilder setDocumento(String documento) {
            this.documento = documento;
            return this;
        }

        public RepartidorBuilder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public RepartidorBuilder setVehiculoAsignado(String vehiculoAsignado) {
            this.vehiculoAsignado = vehiculoAsignado;
            return this;
        }

        public RepartidorBuilder setEstado(estadoRepartidor estado) {
            this.estado = estado;
            return this;
        }

        public RepartidorBuilder setZonaCobertura(String zona) {
            this.zonaCobertura = zona;
            return this;
        }

        public RepartidorBuilder agregarEnvio(String idEnvio) {
            this.enviosAsignados.add(idEnvio);
            return this;
        }

        public Repartidor build() {
            return new Repartidor(idRepartidor, nombreCompleto, documento, telefono, vehiculoAsignado, zonaCobertura, enviosAsignados);
        }
    }
