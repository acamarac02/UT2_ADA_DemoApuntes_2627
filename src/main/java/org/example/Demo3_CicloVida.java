void main() {
    System.out.println("=== CICLO DE VIDA DE ARCHIVOS Y DIRECTORIOS ===");

    try {
        // PASO 1: Asegurar que la carpeta contenedora existe antes de crear archivos
        Path rutaArchivoOriginal = Path.of("datos_app", "logs", "actividad.txt");
        Path carpetaLogs = rutaArchivoOriginal.getParent();

        if (carpetaLogs != null && Files.notExists(carpetaLogs)) {
            Files.createDirectories(carpetaLogs);
            System.out.println("1. Estructura de carpetas creada: " + carpetaLogs);
        }

        // PASO 2: Crear el archivo original vacío
        if (Files.notExists(rutaArchivoOriginal)) {
            Files.createFile(rutaArchivoOriginal);
            System.out.println("2. Archivo original creado: " + rutaArchivoOriginal);
        }

        // PASO 3: Consultar metadatos del archivo
        System.out.println("3. Metadatos del archivo:");
        System.out.println("   - ¿Existe?: " + Files.exists(rutaArchivoOriginal));
        System.out.println("   - ¿Es archivo regular?: " + Files.isRegularFile(rutaArchivoOriginal));
        System.out.println("   - Tamaño inicial: " + Files.size(rutaArchivoOriginal) + " bytes");

        // PASO 4: Crear una copia de respaldo
        Path rutaCopia = Path.of("datos_app", "logs", "actividad_backup.txt");
        Files.copy(rutaArchivoOriginal, rutaCopia, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("4. Archivo copiado a: " + rutaCopia);

        // PASO 5: Mover / Renombrar la copia
        Path rutaHistorico = Path.of("datos_app", "logs", "actividad_historico.txt");
        Files.move(rutaCopia, rutaHistorico, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("5. Archivo renombrado a: " + rutaHistorico);

        // PASO 6: Borrado seguro de los archivos temporales
        boolean borradoOriginal = Files.deleteIfExists(rutaArchivoOriginal);
        boolean borradoHistorico = Files.deleteIfExists(rutaHistorico);
        System.out.println("6. Archivos eliminados con deleteIfExists:");
        System.out.println("   - Original borrado: " + borradoOriginal);
        System.out.println("   - Histórico borrado: " + borradoHistorico);

        // PASO 7: Limpieza de carpetas vacías
        boolean borradaCarpetaLogs = Files.deleteIfExists(carpetaLogs);
        boolean borradaCarpetaPrincipal = Files.deleteIfExists(Path.of("datos_app"));
        System.out.println("7. Carpetas eliminadas:");
        System.out.println("   - Carpeta logs: " + borradaCarpetaLogs);
        System.out.println("   - Carpeta datos_app: " + borradaCarpetaPrincipal);

    } catch (IOException e) {
        System.err.println("Error durante la operación con archivos: " + e.getMessage());
        e.printStackTrace();
    }
}
