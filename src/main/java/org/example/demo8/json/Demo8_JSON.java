import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo8.json.Producto;

void main() {
    System.out.println("=== PROCESAMIENTO DE FORMATO JSON CON JACKSON ===");

    Path carpeta = Path.of("datos_json");
    Path archivoOriginal = carpeta.resolve("catalogo_productos.json");
    Path archivoCopia = carpeta.resolve("catalogo_backup.json");

    // Comprobamos la existencia del archivo antes de operar
    if (Files.notExists(archivoOriginal)) {
        System.err.println("El archivo no existe en: " + archivoOriginal.toAbsolutePath());
        System.err.println("Descarga 'catalogo_productos.json' y colócalo en la carpeta 'datos_json'.");
        return;
    }

    // Instancia principal de Jackson
    ObjectMapper mapper = new ObjectMapper();

    try {
        // PASO 1: Deserializar el archivo JSON a una lista de objetos Java
        System.out.println("\n1. Leyendo catálogo desde '" + archivoOriginal.getFileName() + "'...");
        List<Producto> productos = mapper.readValue(
                archivoOriginal.toFile(),
                new TypeReference<List<Producto>>() {}
        );
        System.out.println("   Productos cargados: " + productos.size());

        // PASO 2: Mostrar los productos leídos por consola
        System.out.println("\n2. Listado de productos:");
        for (Producto p : productos) {
            System.out.println("   > " + p.getNombre() + " (" + p.getCategoria() + ") - " + p.getPrecio() + " €");
            System.out.println("     Etiquetas: " + String.join(", ", p.getEtiquetas()));
        }

        // PASO 3: Modificar un producto existente (rebaja del portátil)
        Producto primerProducto = productos.get(0);
        primerProducto.setPrecio(1049.99);
        System.out.println("\n3. Precio del producto '" + primerProducto.getNombre() + "' rebajado a " + primerProducto.getPrecio() + " €");

        // PASO 4: Añadir un nuevo producto a la lista
        // Creamos una lista mutable auxiliar por si la devuelta fuera de tamaño fijo
        List<Producto> listaActualizada = new ArrayList<>(productos);
        Producto nuevoProducto = new Producto(
                104,
                "Monitor Curvo 34 Pulgadas",
                "Monitores",
                389.00,
                List.of("ultrawide", "pantallas", "oficina")
        );
        listaActualizada.add(nuevoProducto);
        System.out.println("4. Añadido nuevo producto: " + nuevoProducto.getNombre());

        // PASO 5: Guardar los datos actualizados (Dos casos de uso habituales)

        // CASO A: Sobreescribir el propio archivo original (Persistencia directa)
        // Jackson trunca y sobreescribe automáticamente todo el contenido del archivo con el nuevo estado
        System.out.println("\n5.A. Sobreescribiendo el archivo original '" + archivoOriginal.getFileName() + "'...");
        mapper.writerWithDefaultPrettyPrinter().writeValue(archivoOriginal.toFile(), listaActualizada);
        System.out.println("     Archivo original actualizado correctamente.");

        // CASO B: Guardar en un archivo nuevo (Copia de seguridad / Exportación)
        // Ideal si queremos conservar un histórico o snapshot sin alterar la fuente de partida
        System.out.println("\n5.B. Exportando copia de respaldo en '" + archivoCopia.getFileName() + "'...");
        mapper.writerWithDefaultPrettyPrinter().writeValue(archivoCopia.toFile(), listaActualizada);
        System.out.println("     Copia de respaldo generada con éxito (" + listaActualizada.size() + " productos).");

    } catch (IOException e) {
        System.err.println("Error procesando los datos JSON: " + e.getMessage());
        e.printStackTrace();
    }
}
