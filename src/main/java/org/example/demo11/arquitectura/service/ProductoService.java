package org.example.demo11.arquitectura.service;

import org.example.demo11.arquitectura.dao.ConfiguracionDAO;
import org.example.demo11.arquitectura.dao.ProductoDAO;
import org.example.demo11.arquitectura.model.Configuracion;
import org.example.demo11.arquitectura.model.Producto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductoService {

    private final ProductoDAO productoDAO;
    private final ConfiguracionDAO configuracionDAO;

    // Recibe ambos DAOs desacoplados por interfaz
    public ProductoService(ProductoDAO productoDAO, ConfiguracionDAO configuracionDAO) {
        this.productoDAO = productoDAO;
        this.configuracionDAO = configuracionDAO;
    }

    public List<Producto> obtenerCatalogoCompleto() throws IOException {
        return productoDAO.listarTodos();
    }

    public Optional<Producto> consultarProductoPorId(int id) throws IOException {
        return productoDAO.buscarPorId(id);
    }

    public Configuracion obtenerConfiguracion() throws IOException {
        return configuracionDAO.cargar();
    }

    public void registrarNuevoProducto(Producto p) throws IOException {
        // Reglas de negocio: validaciones antes de persistir
        if (p.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio base debe ser estrictamente superior a 0 €.");
        }
        if (p.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        productoDAO.guardar(p);
    }

    /**
     * Calcula el precio final de venta al público (PVP) sumando el IVA configurado en el sistema.
     */
    public double calcularPvpConIva(int idProducto) throws IOException {
        Producto p = productoDAO.buscarPorId(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("No existe el producto #" + idProducto));

        Configuracion config = configuracionDAO.cargar();
        double factorIva = 1 + (config.getIvaPorcentaje() / 100.0);
        return Math.round((p.getPrecio() * factorIva) * 100.0) / 100.0;
    }

    public void aplicarDescuento(int idProducto, double porcentaje) throws IOException {
        if (porcentaje <= 0 || porcentaje > 90) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 1% y 90%.");
        }

        Producto p = productoDAO.buscarPorId(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("No existe ningún producto con ID #" + idProducto));

        double nuevoPrecio = p.getPrecio() * (1 - (porcentaje / 100.0));
        p.setPrecio(Math.round(nuevoPrecio * 100.0) / 100.0);

        productoDAO.actualizar(p);
    }

    public boolean darDeBajaProducto(int idProducto) throws IOException {
        return productoDAO.eliminar(idProducto);
    }
}
