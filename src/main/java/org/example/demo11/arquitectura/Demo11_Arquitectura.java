import org.example.demo11.arquitectura.dao.ConfiguracionDAO;
import org.example.demo11.arquitectura.dao.ConfiguracionPropertiesDAOImpl;
import org.example.demo11.arquitectura.dao.ProductoDAO;
import org.example.demo11.arquitectura.dao.ProductoJsonDAOImpl;
import org.example.demo11.arquitectura.model.Configuracion;
import org.example.demo11.arquitectura.model.Producto;
import org.example.demo11.arquitectura.service.ProductoService;

void main() {
    System.out.println("=== ARQUITECTURA POR CAPAS Y PATRÓN DAO ===");

    // 1. Obtenemos los DAOs mediante sus respectivos Singletons
    // Uno persiste en JSON (Jackson) y el otro en .properties (java.util.Properties)
    ProductoDAO productoDAO = ProductoJsonDAOImpl.getInstance();
    ConfiguracionDAO configuracionDAO = ConfiguracionPropertiesDAOImpl.getInstance();

    // 2. Inicializamos el Servicio inyectando ambos contratos DAO
    ProductoService servicio = new ProductoService(productoDAO, configuracionDAO);

    try {
        // OPERACIÓN 1: Consultar la configuración global del sistema
        System.out.println("\n1. Parámetros de configuración del sistema (app.properties):");
        Configuracion config = servicio.obtenerConfiguracion();
        System.out.println("   " + config);

        // OPERACIÓN 2: Registrar productos iniciales (a través del servicio)
        System.out.println("\n2. Registrando productos en el sistema (productos.json)...");
        servicio.registrarNuevoProducto(new Producto(101, "Portatil Gaming", "Informatica", 1199.99, 15));
        servicio.registrarNuevoProducto(new Producto(102, "Raton Inalambrico", "Accesorios", 29.95, 40));
        servicio.registrarNuevoProducto(new Producto(103, "Monitor 27 Pulgadas", "Monitores", 229.50, 10));
        System.out.println("   Productos guardados correctamente.");

    } catch (IllegalArgumentException e) {
        System.out.println("   Aviso de validación: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("   Error de E/S: " + e.getMessage());
    }

    try {
        // OPERACIÓN 3: Listar todo el catálogo con cálculo de PVP usando la configuración
        System.out.println("\n3. Catálogo completo actual con precio base y PVP final:");
        List<Producto> catalogo = servicio.obtenerCatalogoCompleto();
        for (Producto p : catalogo) {
            double pvp = servicio.calcularPvpConIva(p.getId());
            System.out.printf("   %s --> PVP (con IVA): %.2f €%n", p, pvp);
        }

        // OPERACIÓN 4: Lógica de negocio (Aplicar un 15% de descuento al monitor #103)
        System.out.println("\n4. Aplicando descuento del 15% al producto #103...");
        servicio.aplicarDescuento(103, 15);

        // OPERACIÓN 5: Consultar producto modificado con Optional
        servicio.consultarProductoPorId(103).ifPresent(p -> {
            try {
                double pvpActualizado = servicio.calcularPvpConIva(p.getId());
                System.out.printf("   -> Nuevo estado: %s (PVP final: %.2f €)%n", p, pvpActualizado);
            } catch (IOException e) {
                System.err.println("   Error consultando PVP: " + e.getMessage());
            }
        });

        // OPERACIÓN 6: Dar de baja un producto (#102)
        System.out.println("\n5. Dando de baja el producto #102...");
        boolean eliminado = servicio.darDeBajaProducto(102);
        System.out.println("   ¿Producto eliminado con éxito?: " + eliminado);

        // OPERACIÓN 7: Comprobación final del catálogo persistido
        System.out.println("\n6. Catálogo final tras las operaciones:");
        for (Producto p : servicio.obtenerCatalogoCompleto()) {
            System.out.println("   " + p);
        }

    } catch (IOException e) {
        System.err.println("Error procesando datos: " + e.getMessage());
    }
}
