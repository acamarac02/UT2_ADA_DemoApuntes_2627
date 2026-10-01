void main() {
    System.out.println("=== MANEJO DE ARCHIVOS DE TEXTO PLANO ===");

    Path carpeta = Path.of("datos_texto");
    Path archivoLog = carpeta.resolve("sistema.log");

    try {
        // PASO 1: Asegurar que la carpeta existe
        if (Files.notExists(carpeta)) {
            Files.createDirectories(carpeta);
        }

        // PASO 2: Escribir encabezado inicial sobrescribiendo si existía
        Files.writeString(archivoLog, "=== REGISTRO DEL SISTEMA ===\n",
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("1. Archivo inicial creado.");

        // PASO 3: Añadir eventos al final sin borrar lo anterior (APPEND)
        List<String> eventos = List.of(
                "[INFO] Servidor iniciado correctamente",
                "[INFO] Conexión establecida con el servicio",
                "[WARN] Tiempo de respuesta elevado en consulta",
                "[INFO] Tarea programada ejecutada con éxito"
        );
        Files.write(archivoLog, eventos, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        System.out.println("2. Eventos añadidos con APPEND.");

        // PASO 4: Lectura completa en memoria
        System.out.println("\n3. Lectura completa con Files.readAllLines():");
        List<String> lineasMemoria = Files.readAllLines(archivoLog, StandardCharsets.UTF_8);
        for (String linea : lineasMemoria) {
            System.out.println("   > " + linea);
        }

        // PASO 5: Lectura en flujo continuo línea a línea con Files.lines()
        System.out.println("\n4. Lectura línea a línea con Files.lines():");
        try (Stream<String> lineasFlujo = Files.lines(archivoLog, StandardCharsets.UTF_8)) {
            lineasFlujo.forEach(linea -> {
                System.out.println("   [Stream] " + linea);
            });
        }

    } catch (IOException e) {
        System.err.println("Error en las operaciones de texto plano: " + e.getMessage());
        e.printStackTrace();
    }
}