void main() {
    // ESCRITURA DE CADENAS DE TAMAÑO FIJO (10 caracteres = 20 bytes)
    try (RandomAccessFile raf = new RandomAccessFile("apellidos.dat", "rw")) {
        // Probamos tres casos: corto (3 letras), medio (9 letras) y compuesto largo (11 letras)
        String[] apellidos = {"Gil", "Fernandez", "De la Torre"};

        for (String ape : apellidos) {
            StringBuffer buffer = new StringBuffer(ape);
            buffer.setLength(10); // Forzamos exactamente 10 caracteres
            raf.writeChars(buffer.toString()); // Escribe siempre 20 bytes por apellido
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // LECTURA DE CADENAS FIJAS
    try (RandomAccessFile raf = new RandomAccessFile("apellidos.dat", "r")) {
        // Caso 1: Leemos el segundo apellido "Fernandez" (índice 1 -> 1 * 20 bytes = byte 20)
        raf.seek(20);
        char[] caracteres2 = new char[10];
        for (int i = 0; i < 10; i++) {
            caracteres2[i] = raf.readChar();
        }
        String segundo = new String(caracteres2).replace("\0", "").trim();
        System.out.println("Segundo apellido: " + segundo); // Imprime: Fernandez

        // Caso 2: Leemos el tercer apellido "De la Torre" (índice 2 -> 2 * 20 bytes = byte 40)
        raf.seek(40);
        char[] caracteres3 = new char[10];
        for (int i = 0; i < 10; i++) {
            caracteres3[i] = raf.readChar();
        }
        String tercero = new String(caracteres3).replace("\0", "").trim();
        System.out.println("Tercer apellido (truncado): " + tercero); // Imprime: De la Torr

        // Lectura de todos los apellidos
        raf.seek(0);
        char[] apellido = new char[10];
        // Calculamos el nº apellidos a leer (cada char ocupa 2 bytes y cada cadena ocupa 10 char)
        long numApellidos = raf.length() / Character.BYTES / 10;
        for (int i = 0; i < numApellidos; i++) { // Num apellidos a leer
            for (int car = 0; car < 10; car++) { // Cada apellido tiene 10 char
                apellido[car] = raf.readChar();
            }
            String apellidoCadena = new String(apellido).replace("\0", "").trim();
            System.out.println("Apellido " + i + ": " + apellidoCadena); // Imprime: De la Torr
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}