package co.edu.uniquindio.citycourier.citycourier.model;

import co.edu.uniquindio.citycourier.citycourier.model.ENUMS.NombreCategoria;

public class Categoria {
    private String idCategoria;
    private NombreCategoria nombre;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(String idCategoria, NombreCategoria nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public NombreCategoria getNombre() {
        return nombre;
    }

    public void setNombre(NombreCategoria nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

