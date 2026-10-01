void main() {
    System.out.println("=== GESTIÓN DE FICHEROS DE CONFIGURACIÓN (.PROPERTIES) ===");

    Path rutaConfig = Path.of("config", "app.properties");

    // Comprobamos si el fichero existe antes de intentar leerlo
    if (Files.notExists(rutaConfig)) {
        System.err.println("El archivo no existe en la ruta: " + rutaConfig.toAbsolutePath());
        System.err.println("Descarga 'app.properties' y colócalo en la carpeta 'config'.");
        return;
    }

    Properties propiedades = new Properties();

    try {
        // PASO 1: Cargar el fichero en memoria
        try (InputStream entrada = Files.newInputStream(rutaConfig)) {
            propiedades.load(entrada);
        }
        System.out.println("1. Propiedades cargadas con éxito desde 'app.properties'.");

        // PASO 2: Mostrar los valores leídos por consola
        System.out.println("\n2. Valores leídos de la configuración:");
        System.out.println("   Nombre de la App : " + propiedades.getProperty("app.nombre"));
        System.out.println("   Versión          : " + propiedades.getProperty("app.version"));
        System.out.println("   Puerto actual    : " + propiedades.getProperty("app.puerto"));
        System.out.println("   Modo Debug       : " + propiedades.getProperty("app.modoDebug"));
        System.out.println("   Límite Registros : " + propiedades.getProperty("app.limiteRegistros"));

        // PASO 3: Actualizar una propiedad existente
        System.out.println("\n3. Actualizando el puerto...");
        propiedades.setProperty("app.puerto", "9090");

        // PASO 4: Añadir una nueva propiedad que no existía
        System.out.println("4. Añadiendo nueva propiedad 'app.emailContacto'...");
        propiedades.setProperty("app.emailContacto", "soporte@iesagora.es");

        // PASO 5: Guardar los cambios en el fichero en disco
        try (OutputStream salida = Files.newOutputStream(rutaConfig)) {
            propiedades.store(salida, "Configuracion actualizada por DemoFicherosProperties");
        }
        System.out.println("\n5. Cambios guardados en disco correctamente.");

        // PASO 6: Verificación de los nuevos valores en memoria
        System.out.println("\n--- VALORES FINALES TRAS GUARDAR ---");
        System.out.println("   Nuevo puerto   : " + propiedades.getProperty("app.puerto"));
        System.out.println("   Email contacto : " + propiedades.getProperty("app.emailContacto"));

    } catch (IOException e) {
        System.err.println("Error procesando el fichero de propiedades: " + e.getMessage());
        e.printStackTrace();
    }
}