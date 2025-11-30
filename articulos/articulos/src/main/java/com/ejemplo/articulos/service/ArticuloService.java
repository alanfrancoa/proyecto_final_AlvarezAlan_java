package com.ejemplo.articulos.service;

import java.util.List;
import java.util.Optional;
import com.ejemplo.articulos.model.Articulo;

public interface ArticuloService {

    // Declaración de métodos para la gestión de artículos
    List<Articulo> listarArticulos();
    Optional<Articulo> obtenerArticuloPorId(Long id);
    Articulo guardarArticulo(Articulo articulo);
    Articulo actualizarArticulo(Long id, Articulo articulo);
    void eliminarArticulo(Long id);

    //Metodos de busqueda con filtros
    List<Articulo> buscarPorNombre(String nombre);
    List<Articulo> buscarPorPrecioMinimo(Double precioMinimo);
    List<Articulo> buscarPorPrecioMaximo(Double precioMaximo);
    List<Articulo> buscarPorPrecioEntre(Double precioMinimo, Double precioMaximo);
    List<Articulo> buscarPorNombreYPrecioEntre(String nombre, Double precioMinimo, Double precioMaximo);

}
