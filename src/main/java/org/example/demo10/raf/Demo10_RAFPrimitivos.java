void main() {
    // 1. Escribimos 10 números enteros en el archivo
    try (RandomAccessFile raf = new RandomAccessFile("numeros.dat", "rw")) {
        for (int i = 1; i <= 10; i++) {
            raf.writeInt(i * 10); // Cada entero ocupa 4 bytes
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // 2. Lectura secuencial de todos los enteros
    try (RandomAccessFile raf = new RandomAccessFile("numeros.dat", "r")) {
        // Mientras el puntero no alcance el final del archivo (length en bytes)
        while (raf.getFilePointer() < raf.length()) {
            int numero = raf.readInt();
            System.out.print(numero + " ");
        }
        System.out.println();
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // 3. Acceso directo a una posición concreta
    try (RandomAccessFile raf = new RandomAccessFile("numeros.dat", "r")) {
        int posicionDeseada = 6;
        long offset = (posicionDeseada - 1) * Integer.BYTES;

        // Saltamos directamente al byte 20
        raf.seek(offset);
        int sextoNumero = raf.readInt();
        System.out.println("El 6º número es: " + sextoNumero); // Imprime 60
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    // Acceso a posición incorrecta
    try (RandomAccessFile raf = new RandomAccessFile("numeros.dat", "rw")) {

        // Insertamos los primeros 10 números naturales (0 al 9)
        for (int i = 0; i < 10; i++) {
            raf.writeInt(i);
        }

        // Un int ocupa 4 bytes por lo que cada elemento estará en
        // una posición múltiplo de 4: 0, 4, 8, etc.

        // Vamos a suponer que intentamos leer el 6º entero (4 * 5 = 20)
        // pero nos hemos confundido y hemos leído desde el byte 19:
        raf.seek(19);

        // Desglose de bytes en memoria binaria:
        // Bytes 0 - 3   (num = 0)  00000000 00000000 00000000 00000000
        // Bytes 4 - 7   (num = 1)  00000000 00000000 00000000 00000001
        // Bytes 8 - 11  (num = 2)  00000000 00000000 00000000 00000010
        // Bytes 12 - 15 (num = 3)  00000000 00000000 00000000 00000011
        // Bytes 16 - 19 (num = 4)  00000000 00000000 00000000 00000100  <-- el byte 19 tiene el valor 00000100
        // Bytes 20 - 23 (num = 5)  00000000 00000000 00000000 00000101  <-- el 6º número empieza en el byte 20
        // Bytes 24 - 27 (num = 6)  00000000 00000000 00000000 00000110

        // Leemos 4 bytes a partir del byte 19 (bytes 19 a 22):
        int sextoNumero = raf.readInt();
        System.out.println("El sexto número es: " + sextoNumero);
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}