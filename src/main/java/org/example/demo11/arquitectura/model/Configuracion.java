package org.example.demo11.arquitectura.model;

public class Configuracion {

    private String nombreTienda;
    private double ivaPorcentaje;
    private String moneda;

    public Configuracion() {
    }

    public Configuracion(String nombreTienda, double ivaPorcentaje, String moneda) {
        this.nombreTienda = nombreTienda;
        this.ivaPorcentaje = ivaPorcentaje;
        this.moneda = moneda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public double getIvaPorcentaje() {
        return ivaPorcentaje;
    }

    public void setIvaPorcentaje(double ivaPorcentaje) {
        this.ivaPorcentaje = ivaPorcentaje;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return String.format("Tienda: %s | IVA: %.1f%% | Moneda: %s",
                nombreTienda, ivaPorcentaje, moneda);
    }
}
