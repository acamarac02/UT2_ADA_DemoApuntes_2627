//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    System.out.println("=== MANEJO DE RUTAS CON PATH ===");

    // 1. Creación de una ruta relativa multiplataforma
    Path rutaRelativa = Path.of("datos", "almacen", "inventario.dat");
    System.out.println("Ruta: " + rutaRelativa);
    System.out.println("Nombre del archivo: " + rutaRelativa.getFileName());
    System.out.println("Directorio padre: " + rutaRelativa.getParent());

    // 2. Generación dinámica de ruta absoluta
    Path rutaAbsoluta = rutaRelativa.toAbsolutePath();
    System.out.println("Ruta absoluta dinámica: " + rutaAbsoluta);

    // 3. Concatenación de rutas con resolve()
    Path directorioBase = Path.of("configuracion");
    Path archivoAjustes = directorioBase.resolve("app.properties");
    System.out.println("Ruta concatenada: " + archivoAjustes);

    // 4. Limpieza de rutas con normalize()
    Path rutaSucia = Path.of("datos", "almacen", "..", "almacen", "backups", ".", "copia.bin");
    System.out.println("Ruta con redundancias: " + rutaSucia);
    System.out.println("Ruta limpia: " + rutaSucia.normalize());
}
