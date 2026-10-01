package org.example.demo9.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    // isAttribute = true indica que se lee del atributo <producto id="101" ...>
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @JacksonXmlProperty(isAttribute = true)
    private String categoria;

    // Elementos hijos directos
    private String nombre;
    private double precio;
    private int stock;

    // Objeto anidado: Jackson mapea automáticamente los campos dentro de <proveedor>
    private Proveedor proveedor;

    // Lista envuelta: <etiquetas> es el contenedor que agrupa cada <etiqueta>
    @JacksonXmlElementWrapper(localName = "etiquetas")
    @JacksonXmlProperty(localName = "etiqueta")
    private List<String> etiquetas;

    // Constructor vacío OBLIGATORIO para Jackson
    public Producto() {
        this.etiquetas = new ArrayList<>();
    }

    public Producto(int id, String categoria, String nombre, double precio, int stock, Proveedor proveedor, List<String> etiquetas) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
        this.etiquetas = (etiquetas != null) ? new ArrayList<>(etiquetas) : new ArrayList<>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
                ", categoria='" + categoria + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio + " €" +
                ", stock=" + stock +
                ", proveedor=" + proveedor +
                ", etiquetas=" + etiquetas +
                '}';
    }
}
