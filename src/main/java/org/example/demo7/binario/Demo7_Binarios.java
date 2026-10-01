import org.example.demo7.binario.EstadoPartida;

void main() {
    System.out.println("=== SERIALIZACIÓN Y PERSISTENCIA BINARIA DE OBJETOS ===");

    Path carpeta = Path.of("datos_binarios");
    Path archivoPartida = carpeta.resolve("partida_guardada.bin");

    try {
        // PASO 1: Asegurar que la carpeta de destino existe
        if (Files.notExists(carpeta)) {
            Files.createDirectories(carpeta);
        }

        // PASO 2: Crear el objeto en memoria con sus datos iniciales
        System.out.println("\n1. Creando partida en memoria...");
        EstadoPartida partidaActual = new EstadoPartida("AdaLovelace", 12, 4580, "TOKEN-XYZ-9981");
        partidaActual.agregarLogro("Primeros pasos");
        partidaActual.agregarLogro("Explorador novato");
        partidaActual.agregarLogro("Maestro de la lógica");

        System.out.println("   Objeto original antes de guardar:");
        System.out.println("   " + partidaActual);

        // PASO 3: Serializar el objeto hacia el archivo binario
        System.out.println("\n2. Guardando estado en archivo binario '" + archivoPartida.getFileName() + "'...");
        try (OutputStream flujoArchivo = Files.newOutputStream(archivoPartida);
             ObjectOutputStream oos = new ObjectOutputStream(flujoArchivo)) {

            oos.writeObject(partidaActual);
        }
        System.out.println("   Partida serializada y guardada con éxito.");

        // PASO 4: Deserializar el objeto desde el archivo binario
        System.out.println("\n3. Leyendo y recuperando el objeto desde el disco...");
        EstadoPartida partidaRecuperada;
        try (InputStream flujoLectura = Files.newInputStream(archivoPartida);
             ObjectInputStream ois = new ObjectInputStream(flujoLectura)) {

            partidaRecuperada = (EstadoPartida) ois.readObject();
        }

        // PASO 5: Verificar los datos restaurados y el comportamiento de transient
        System.out.println("   Objeto restaurado en memoria:");
        System.out.println("   " + partidaRecuperada);

        System.out.println("\n--- COMPROBACIÓN DE CAMPOS ---");
        System.out.println("   Jugador             : " + partidaRecuperada.getNombreJugador());
        System.out.println("   Nivel               : " + partidaRecuperada.getNivel());
        System.out.println("   Logros guardados    : " + partidaRecuperada.getLogrosDesbloqueados().size());
        System.out.println("   Token temporal      : " + partidaRecuperada.getTokenSesionTemporal()
                + " (nulo porque se marcó como transient)");

    } catch (IOException e) {
        System.err.println("Error de entrada/salida en el archivo binario: " + e.getMessage());
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        System.err.println("No se encontró la definición de la clase a recuperar: " + e.getMessage());
        e.printStackTrace();
    }
}