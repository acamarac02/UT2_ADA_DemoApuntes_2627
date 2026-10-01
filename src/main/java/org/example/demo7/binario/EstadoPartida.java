package org.example.demo7.binario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstadoPartida implements Serializable {

    // Identificador único para el control de versiones de la clase
    private static final long serialVersionUID = 1L;

    private String nombreJugador;
    private int nivel;
    private int puntosExperiencia;
    private List<String> logrosDesbloqueados;

    // Campo efímero: no queremos guardarlo en disco
    private transient String tokenSesionTemporal;

    public EstadoPartida(String nombreJugador, int nivel, int puntosExperiencia, String tokenSesionTemporal) {
        this.nombreJugador = nombreJugador;
        this.nivel = nivel;
        this.puntosExperiencia = puntosExperiencia;
        this.tokenSesionTemporal = tokenSesionTemporal;
        this.logrosDesbloqueados = new ArrayList<>();
    }

    public void agregarLogro(String logro) {
        this.logrosDesbloqueados.add(logro);
    }

    // Getters
    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosExperiencia() {
        return puntosExperiencia;
    }

    public List<String> getLogrosDesbloqueados() {
        return logrosDesbloqueados;
    }

    public String getTokenSesionTemporal() {
        return tokenSesionTemporal;
    }

    @Override
    public String toString() {
        return "EstadoPartida{" +
                "nombreJugador='" + nombreJugador + '\'' +
                ", nivel=" + nivel +
                ", puntosExperiencia=" + puntosExperiencia +
                ", logrosDesbloqueados=" + logrosDesbloqueados +
                ", tokenSesionTemporal='" + tokenSesionTemporal + '\'' +
                '}';
    }
}
