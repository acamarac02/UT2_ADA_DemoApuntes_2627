private static final String RUTA_FICHERO = "datos_raf/empleados.dat";

// Constantes de diseño para los registros de tamaño fijo
public static final int LONGITUD_APELLIDO = 10; // 10 caracteres * 2 bytes = 20 bytes
public static final int TAMANO_REGISTRO = Integer.BYTES                      // id (4 bytes)
        + (LONGITUD_APELLIDO * Character.BYTES)                              // apellido (20 bytes)
        + Integer.BYTES                                                      // depto (4 bytes)
        + Double.BYTES;                                                      // salario (8 bytes)
// Total = 36 bytes por registro


void main() {
    System.out.println("=== GESTIÓN DE FICHEROS DE ACCESO ALEATORIO (RAF) ===\n");

    // Creamos la carpeta contenedora si no existe
    Path rutaDirectorio = Path.of("datos_raf");
    try {
        if (Files.notExists(rutaDirectorio)) {
            Files.createDirectories(rutaDirectorio);
        }
    } catch (IOException e) {
        System.err.println("No se pudo crear la carpeta: " + e.getMessage());
        return;
    }

    // Datos iniciales de prueba en arrays paralelos
    int[] ids = {1, 2, 3, 4};
    String[] apellidos = {"Garcia", "Fernandez", "Lopez", "Martinez"};
    int[] departamentos = {10, 20, 10, 30};
    double[] salarios = {1850.00, 2100.50, 1950.00, 2400.75};

    try {
        // PASO 1: Escribir los registros iniciales en el fichero binario
        escribirEmpleados(ids, apellidos, departamentos, salarios);

        // PASO 2: Mostrar todos los empleados leyendo secuencialmente de disco
        System.out.println("1. Listado completo de empleados:");
        mostrarTodos();

        // PASO 3: Acceso directo por posición (Consultar el departamento del empleado 4)
        System.out.println("\n2. Acceso directo al departamento del empleado #4:");
        consultarDepartamento(4);

        // PASO 4: Modificación quirúrgica (Actualizar salario del empleado 2 directamente en disco)
        System.out.println("\n3. Actualizando salario del empleado #2 a 2850.50 €...");
        modificarSalario(2, 2850.50);

        // PASO 5: Comprobar el cambio volviendo a listar
        System.out.println("\n4. Listado tras la actualización quirúrgica en disco:");
        mostrarTodos();

    } catch (IOException e) {
        System.err.println("Error operando con el fichero RAF: " + e.getMessage());
        e.printStackTrace();
    }
}


/**
 * Escribe los registros iniciales en el archivo binario asegurando el tamaño fijo de 36 bytes.
 */
public static void escribirEmpleados(int[] ids, String[] apellidos, int[] departamentos, double[] salarios) throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(RUTA_FICHERO, "rw")) {
        raf.setLength(0); // Vaciamos el fichero si ya existía de pruebas anteriores

        for (int i = 0; i < ids.length; i++) {
            // 1. ID (4 bytes)
            raf.writeInt(ids[i]);

            // 2. Apellido con tamaño fijo de 10 caracteres (20 bytes)
            StringBuffer buffer = new StringBuffer(apellidos[i]);
            buffer.setLength(LONGITUD_APELLIDO);
            raf.writeChars(buffer.toString());

            // 3. Departamento (4 bytes)
            raf.writeInt(departamentos[i]);

            // 4. Salario (8 bytes)
            raf.writeDouble(salarios[i]);
        }
        System.out.println("-> Fichero creado con éxito. Tamaño total: " + raf.length()
                + " bytes (" + TAMANO_REGISTRO + " B por registro).\n");
    }
}

/**
 * Recorre secuencialmente todos los registros leyendo directamente de disco campo a campo.
 */
public static void mostrarTodos() throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(RUTA_FICHERO, "r")) {
        while (raf.getFilePointer() < raf.length()) {
            int id = raf.readInt();

            // Leemos los 10 caracteres del apellido
            char[] caracteres = new char[LONGITUD_APELLIDO];
            for (int i = 0; i < LONGITUD_APELLIDO; i++) {
                caracteres[i] = raf.readChar();
            }
            String apellido = new String(caracteres).replace("\0", "").trim();

            int departamento = raf.readInt();
            double salario = raf.readDouble();

            System.out.printf("   [ID: %d] Apellido: %-10s | Depto: %d | Salario: %.2f €%n",
                    id, apellido, departamento, salario);
        }
    }
}

/**
 * Salta directamente a consultar el campo departamento de un empleado mediante su offset.
 */
public static void consultarDepartamento(int numeroEmpleado) throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(RUTA_FICHERO, "r")) {
        // Offset dinámico: saltar (N - 1) registros + ID (4B) + Apellido (20B)
        long offset = (long) (numeroEmpleado - 1) * TAMANO_REGISTRO
                + Integer.BYTES + (LONGITUD_APELLIDO * Character.BYTES);

        if (offset >= raf.length()) {
            System.out.println("   El empleado #" + numeroEmpleado + " no existe.");
            return;
        }

        raf.seek(offset); // Salto directo en tiempo constante O(1)
        int departamento = raf.readInt();
        System.out.println("   -> Puntero situado en el byte " + offset
                + " | Departamento del empleado #" + numeroEmpleado + ": " + departamento);
    }
}

/**
 * Modifica quirúrgicamente únicamente los 8 bytes del salario del empleado indicado.
 */
public static void modificarSalario(int numeroEmpleado, double nuevoSalario) throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(RUTA_FICHERO, "rw")) {
        // Offset dinámico: saltar (N - 1) registros + ID (4B) + Apellido (20B) + Depto (4B) = (N-1)*36 + 28
        long offset = (long) (numeroEmpleado - 1) * TAMANO_REGISTRO
                + Integer.BYTES + (LONGITUD_APELLIDO * Character.BYTES) + Integer.BYTES;

        if (offset + Double.BYTES > raf.length()) {
            System.out.println("   El empleado #" + numeroEmpleado + " no existe.");
            return;
        }

        raf.seek(offset); // Salto directo a los 8 bytes del salario
        raf.writeDouble(nuevoSalario); // Sobreescribe solo el salario sin tocar nada más
        System.out.println("   -> Salario actualizado quirúrgicamente en el byte " + offset + " con éxito.");
    }
}