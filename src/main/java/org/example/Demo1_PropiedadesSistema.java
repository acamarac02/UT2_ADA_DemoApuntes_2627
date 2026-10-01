void main() {
    System.out.println("=== CONFIGURACIÓN DEL SISTEMA OPERATIVO ===");

    // Separadores de archivos y líneas
    String separadorRuta = System.getProperty("file.separator");
    String separadorLinea = System.getProperty("line.separator");

    System.out.println("Separador de rutas (file.separator): " + separadorRuta);
    System.out.println("Longitud salto de línea (bytes): " + separadorLinea.getBytes().length);

    // Directorios del usuario y del proyecto
    String directorioActual = System.getProperty("user.dir");
    String carpetaPersonal = System.getProperty("user.home");
    String versionJava = System.getProperty("java.version");

    System.out.println("Directorio de trabajo actual (user.dir): " + directorioActual);
    System.out.println("Carpeta personal del usuario (user.home): " + carpetaPersonal);
    System.out.println("Versión de Java en ejecución: " + versionJava);
}