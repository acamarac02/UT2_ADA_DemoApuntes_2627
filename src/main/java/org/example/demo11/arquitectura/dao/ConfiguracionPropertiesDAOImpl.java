package org.example.demo11.arquitectura.dao;


import org.example.demo11.arquitectura.model.Configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfiguracionPropertiesDAOImpl implements ConfiguracionDAO {

    // 1. Instancia única (Singleton)
    private static ConfiguracionPropertiesDAOImpl instancia;

    // Configuración interna de persistencia
    private final Path rutaArchivo;

    // 2. Constructor PRIVADO
    private ConfiguracionPropertiesDAOImpl() {
        this.rutaArchivo = Path.of("datos_dao", "app.properties");
        asegurarArchivoPorDefecto();
    }

    // 3. Punto de acceso global (Singleton thread-safe)
    public static synchronized ConfiguracionPropertiesDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracionPropertiesDAOImpl();
        }
        return instancia;
    }

    private void asegurarArchivoPorDefecto() {
        try {
            if (rutaArchivo.getParent() != null && Files.notExists(rutaArchivo.getParent())) {
                Files.createDirectories(rutaArchivo.getParent());
            }
            if (Files.notExists(rutaArchivo)) {
                // Creamos el archivo de configuración inicial predefinido si no existe
                Properties prop = new Properties();
                prop.setProperty("app.tienda.nombre", "TechStore Ágora");
                prop.setProperty("app.tienda.iva", "21.0");
                prop.setProperty("app.tienda.moneda", "EUR");
                try (OutputStream out = Files.newOutputStream(rutaArchivo)) {
                    prop.store(out, "Configuracion Global del Sistema ADA (Solo Lectura)");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error inicializando app.properties: " + e.getMessage(), e);
        }
    }

    @Override
    public Configuracion cargar() throws IOException {
        Properties prop = new Properties();
        try (InputStream in = Files.newInputStream(rutaArchivo)) {
            prop.load(in);
        }

        String nombreTienda = prop.getProperty("app.tienda.nombre", "TechStore Ágora");
        double iva = Double.parseDouble(prop.getProperty("app.tienda.iva", "21.0"));
        String moneda = prop.getProperty("app.tienda.moneda", "EUR");

        return new Configuracion(nombreTienda, iva, moneda);
    }
}
