package org.example.demo11.arquitectura.dao;

import org.example.demo11.arquitectura.model.Configuracion;

import java.io.IOException;

public interface ConfiguracionDAO {

    /**
     * Carga y devuelve los parámetros de configuración predefinidos del sistema.
     * Es una operación de solo lectura (no permite modificación externa).
     */
    Configuracion cargar() throws IOException;
}
