package org.example.demo8.json;

import java.util.List;

public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private List<String> etiquetas;

    // Constructor vacío OBLIGATORIO para que Jackson pueda instanciar la clase por reflexión
    public Producto() {
    }

    public Producto(int id, String nombre, String categoria, double precio, List<String> etiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.etiquetas = etiquetas;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio + " €" +
                ", etiquetas=" + etiquetas +
                '}';
    }
}
