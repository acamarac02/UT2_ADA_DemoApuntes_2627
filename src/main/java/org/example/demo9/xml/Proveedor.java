package org.example.demo9.xml;

public class Proveedor {

    private String cif;
    private String nombre;
    private String pais;

    // Constructor vacío OBLIGATORIO para Jackson
    public Proveedor() {
    }

    public Proveedor(String cif, String nombre, String pais) {
        this.cif = cif;
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return nombre + " (" + pais + ", CIF: " + cif + ")";
    }
}
