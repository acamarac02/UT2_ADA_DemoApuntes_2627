package org.example.demo11.arquitectura.dao;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo11.arquitectura.model.Producto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoJsonDAOImpl implements ProductoDAO {

    // 1. Instancia única (Singleton)
    private static ProductoJsonDAOImpl instancia;

    // Configuración interna de persistencia
    private final Path rutaArchivo;
    private final ObjectMapper mapper;

    // 2. Constructor PRIVADO
    private ProductoJsonDAOImpl() {
        this.rutaArchivo = Path.of("datos_dao", "productos.json");
        this.mapper = new ObjectMapper();
        asegurarDirectorioYArchivo();
    }

    // 3. Método de acceso global a la instancia única
    public static synchronized ProductoJsonDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new ProductoJsonDAOImpl();
        }
        return instancia;
    }

    private void asegurarDirectorioYArchivo() {
        try {
            if (rutaArchivo.getParent() != null && Files.notExists(rutaArchivo.getParent())) {
                Files.createDirectories(rutaArchivo.getParent());
            }
            if (Files.notExists(rutaArchivo)) {
                // Si el archivo no existe, lo inicializamos con una lista vacía
                mapper.writerWithDefaultPrettyPrinter().writeValue(rutaArchivo.toFile(), new ArrayList<Producto>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error inicializando el archivo JSON del DAO: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Producto> listarTodos() throws IOException {
        if (Files.notExists(rutaArchivo) || Files.size(rutaArchivo) == 0) {
            return new ArrayList<>();
        }
        return mapper.readValue(rutaArchivo.toFile(), new TypeReference<List<Producto>>() {});
    }

    @Override
    public Optional<Producto> buscarPorId(int id) throws IOException {
        List<Producto> productos = listarTodos();
        for (Producto p : productos) {
            if (p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public void guardar(Producto producto) throws IOException {
        List<Producto> productos = new ArrayList<>(listarTodos());

        // Comprobamos que el ID no esté duplicado con un bucle tradicional
        for (Producto p : productos) {
            if (p.getId() == producto.getId()) {
                throw new IllegalArgumentException("Ya existe un producto con el ID #" + producto.getId());
            }
        }

        productos.add(producto);
        volcarADisco(productos);
    }

    @Override
    public void actualizar(Producto producto) throws IOException {
        List<Producto> productos = new ArrayList<>(listarTodos());
        boolean encontrado = false;

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("No se encontró el producto #" + producto.getId() + " para actualizar.");
        }

        volcarADisco(productos);
    }

    @Override
    public boolean eliminar(int id) throws IOException {
        List<Producto> productos = new ArrayList<>(listarTodos());
        boolean eliminado = false;

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) {
                productos.remove(i);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            volcarADisco(productos);
        }
        return eliminado;
    }

    private void volcarADisco(List<Producto> productos) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(rutaArchivo.toFile(), productos);
    }
}
