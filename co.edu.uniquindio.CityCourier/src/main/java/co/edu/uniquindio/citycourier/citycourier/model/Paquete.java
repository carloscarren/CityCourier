package co.edu.uniquindio.citycourier.citycourier.model;

import java.io.Serializable;

/**
 * 📦 Representa un paquete que forma parte de un envío en el sistema CityCourier.
 * Contiene información sobre su contenido, dimensiones, peso y valor declarado.
 */
public class Paquete implements Serializable {


    private String idPaquete;
    private String descripcion;
    private double peso;        // En kilogramos
    private double volumen;     // En metros cúbicos
    private double valorDeclarado; // Valor del contenido
    private String contenido;
        public Paquete() {
    }

    public Paquete(String idPaquete, String descripcion, double peso,
                   double volumen, double valorDeclarado, String contenido) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.peso = peso;
        this.volumen = volumen;
        this.valorDeclarado = valorDeclarado;
        this.contenido = contenido;
    }
    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(double valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }


    @Override
    public String toString() {
        return "Paquete{" +
                "idPaquete='" + idPaquete + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", peso=" + peso +
                ", volumen=" + volumen +
                ", valorDeclarado=" + valorDeclarado +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
