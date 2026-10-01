void main() {
    System.out.println("=== LECTURA Y PROCESAMIENTO DE ARCHIVO CSV ===");

    Path rutaCsv = Path.of("datos_csv", "catalogo_productos.csv");
    String separador = ",";

    // Comprobamos si el archivo existe antes de intentar leerlo
    if (Files.notExists(rutaCsv)) {
        System.err.println("El archivo no existe en la ruta: " + rutaCsv.toAbsolutePath());
        System.err.println("Descarga 'catalogo_productos.csv' y colócalo en la carpeta 'datos_csv'.");
        return;
    }

    try {
        // PASO 1: Leer todas las líneas del archivo CSV
        List<String> lineas = Files.readAllLines(rutaCsv, StandardCharsets.UTF_8);
        System.out.println("Líneas leídas en total (incluyendo cabecera): " + lineas.size());

        // PASO 2: Procesar los datos fila a fila
        // Empezamos en i = 1 para omitir la cabecera (nombres de columnas en i = 0)
        System.out.println("\n--- LISTADO COMPLETO DE PRODUCTOS ---");

        for (int i = 1; i < lineas.size(); i++) {
            String linea = lineas.get(i);

            // Evitamos procesar líneas vacías
            if (linea.isBlank()) {
                continue;
            }

            // PASO 3: Trocear la línea en columnas según el separador
            String[] campos = linea.split(separador);

            // Extraemos cada valor por su posición de columna directamente como texto
            String id = campos[0].trim();
            String nombre = campos[1].trim();
            String precio = campos[2].trim();
            String stock = campos[3].trim();
            String disponible = campos[4].trim();

            // PASO 4: Mostrar la información formateada por consola
            System.out.println("Producto #" + id + ": " + nombre);
            System.out.println("   Precio     : " + precio + " €");
            System.out.println("   Stock      : " + stock + " unidades");
            System.out.println("   Disponible : " + disponible);
            System.out.println("   -----------------------------------");
        }

    } catch (IOException e) {
        System.err.println("Error leyendo el archivo CSV: " + e.getMessage());
        e.printStackTrace();
    }
}