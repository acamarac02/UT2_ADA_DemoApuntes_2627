package org.example.demo11.arquitectura.dao;

import org.example.demo11.arquitectura.model.Producto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductoDAO {

    /**
     * Recupera todos los productos disponibles en el almacén de datos.
     */
    List<Producto> listarTodos() throws IOException;

    /**
     * Busca un producto específico a partir de su identificador único.
     */
    Optional<Producto> buscarPorId(int id) throws IOException;

    /**
     * Inserta un nuevo producto en el almacén de datos.
     */
    void guardar(Producto producto) throws IOException;

    /**
     * Actualiza la información de un producto existente.
     */
    void actualizar(Producto producto) throws IOException;

    /**
     * Elimina un producto del almacén por su ID.
     * @return true si se eliminó con éxito, false si no se encontró.
     */
    boolean eliminar(int id) throws IOException;
}