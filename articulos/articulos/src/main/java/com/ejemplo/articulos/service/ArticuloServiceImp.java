package com.ejemplo.articulos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.articulos.model.Articulo;
import com.ejemplo.articulos.repository.ArticuloRepository;


@Service
public class ArticuloServiceImp implements ArticuloService {
    
    // Inyección del repositorio de artículos
    private final ArticuloRepository articuloRepository;

    // Constructor con inyección de dependencias
    @Autowired
    public ArticuloServiceImp(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    // Implementación de los métodos definidos en la interfaz para gestion de articulos
    @Override
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    @Override
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return articuloRepository.findById(id);
    }

    @Override
    public Articulo guardarArticulo(Articulo articulo) {
        if (articulo == null) {
            throw new IllegalArgumentException("El artículo no puede ser nulo");
        }
        return articuloRepository.save(articulo);
    }

    @Override
    public Articulo actualizarArticulo(Long id, Articulo articulo) {
        articulo.setId(id);
        return articuloRepository.save(articulo);
    }

    @Override
    public void eliminarArticulo(Long id) {
        if (id != null) {
            articuloRepository.deleteById(id);
        }
        
    }

    // Implementación de los métodos de búsqueda con filtros
    @Override
    public List<Articulo> buscarPorNombre(String nombre) {
        return articuloRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Articulo> buscarPorPrecioMinimo(Double precioMinimo) {
        return articuloRepository.findByPrecioGreaterThanEqual(precioMinimo);
    }

    @Override
    public List<Articulo> buscarPorPrecioMaximo(Double precioMaximo) {
        return articuloRepository.findByPrecioLessThanEqual(precioMaximo);
    }   

    @Override
    public List<Articulo> buscarPorPrecioEntre(Double precioMinimo, Double precioMaximo) {
        return articuloRepository.findByPrecioBetween(precioMinimo, precioMaximo);
    }

    @Override
    public List<Articulo> buscarPorNombreYPrecioEntre(String nombre, Double precioMinimo, Double precioMaximo) {
        return articuloRepository.findByNombreContainingIgnoreCaseAndPrecioBetween(nombre, precioMinimo, precioMaximo);
    }
    
}