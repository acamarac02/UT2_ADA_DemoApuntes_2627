import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.demo9.xml.Producto;
import org.example.demo9.xml.Catalogo;
import org.example.demo9.xml.Proveedor;

void main() {
    System.out.println("=== PROCESAMIENTO DE FORMATO XML CON JACKSON ===");

    Path carpeta = Path.of("datos_xml");
    Path archivoOriginal = carpeta.resolve("catalogo_productos.xml");
    Path archivoCopia = carpeta.resolve("catalogo_backup.xml");

    // Comprobamos la existencia del archivo antes de operar
    if (Files.notExists(archivoOriginal)) {
        System.err.println("El archivo no existe en: " + archivoOriginal.toAbsolutePath());
        System.err.println("Descarga 'catalogo_productos.xml' y colócalo en la carpeta 'datos_xml'.");
        return;
    }

    // Instancia principal de Jackson XML
    XmlMapper xmlMapper = new XmlMapper();

    try {
        // PASO 1: Deserializar el archivo XML completo al objeto Catalogo
        System.out.println("\n1. Leyendo catálogo XML desde '" + archivoOriginal.getFileName() + "'...");
        Catalogo catalogo = xmlMapper.readValue(archivoOriginal.toFile(), Catalogo.class);
        System.out.println("   Productos encontrados: " + catalogo.getProductos().size());

        // PASO 2: Mostrar los productos leídos por consola (incluyendo objeto y lista interna)
        System.out.println("\n2. Listado de productos parseados:");
        for (Producto p : catalogo.getProductos()) {
            System.out.println("   [ID " + p.getId() + "] " + p.getNombre()
                    + " (" + p.getCategoria() + ") - " + p.getPrecio() + " €"
                    + " | Stock: " + p.getStock() + " ud.");
            System.out.println("     Proveedor: " + p.getProveedor());
            System.out.println("     Etiquetas: " + String.join(", ", p.getEtiquetas()));
        }

        // PASO 3: Modificar un producto existente (incrementar stock y añadir etiqueta)
        for (Producto p : catalogo.getProductos()) {
            if (p.getId() == 103) {
                p.setStock(p.getStock() + 10);
                p.getEtiquetas().add("oferta");
                System.out.println("\n3. Producto #" + p.getId() + " actualizado -> "
                        + "Nuevo stock: " + p.getStock() + " | Etiquetas: " + p.getEtiquetas());
                break;
            }
        }

        // PASO 4: Añadir un nuevo producto completo con su proveedor y lista de etiquetas
        Proveedor nuevoProveedor = new Proveedor("B55443322", "AudioPro Iberia", "España");
        List<String> nuevasEtiquetas = new ArrayList<>(List.of("audio", "bluetooth", "portatil"));
        Producto nuevoProducto = new Producto(
                105,
                "Audio",
                "Altavoces Bluetooth 20W",
                49.99,
                12,
                nuevoProveedor,
                nuevasEtiquetas
        );
        catalogo.getProductos().add(nuevoProducto);
        System.out.println("\n4. Añadido nuevo producto: " + nuevoProducto.getNombre()
                + " con proveedor " + nuevoProveedor.getNombre());

        // PASO 5: Guardar los datos actualizados (Dos casos de uso habituales)

        // CASO A: Sobreescribir el propio archivo original (Persistencia directa)
        // Jackson trunca y sobreescribe automáticamente todo el contenido XML con el nuevo estado
        System.out.println("\n5.A. Sobreescribiendo el archivo original '" + archivoOriginal.getFileName() + "'...");
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(archivoOriginal.toFile(), catalogo);
        System.out.println("     Archivo original actualizado correctamente.");

        // CASO B: Guardar en un archivo nuevo (Copia de seguridad / Exportación)
        // Ideal si queremos conservar un histórico o snapshot sin alterar la fuente de partida
        System.out.println("\n5.B. Exportando copia de respaldo en '" + archivoCopia.getFileName() + "'...");
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(archivoCopia.toFile(), catalogo);
        System.out.println("     Copia de respaldo generada con éxito.");

    } catch (IOException e) {
        System.err.println("Error procesando el archivo XML: " + e.getMessage());
        e.printStackTrace();
    }
}
