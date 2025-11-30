package com.ejemplo.articulos.repository;
import com.ejemplo.articulos.model.Articulo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // Metodos CRUD se heredan de JpaRepository:
    // List<Articulo> findAll();
    // Optional<Articulo> findById(Long id);
    // Articulo save(Articulo articulo);
    // void deleteById(Long id);
    // count(), existsById(), etc.
    
    //Metodos personalizados:

    //Busqueda de articulos por nombre (contiene, sin distinguir mayusculas/minusculas)
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);

    //Busqueda de articulos por precio mayor o igual a un valor dado
    List<Articulo> findByPrecioGreaterThanEqual(Double precioMinimo);

    //Busqueda de articulos por precio menor o igual a un valor dado
    List<Articulo> findByPrecioLessThanEqual(Double precioMaximo);

    //Busqueda de articulos con precio entre dos valores dados
    List<Articulo> findByPrecioBetween(Double precioMinimo, Double precioMaximo);

    //Buscar articulos filtrando por nombre y rango de precio
    List<Articulo> findByNombreContainingIgnoreCaseAndPrecioBetween(String nombre, Double precioMinimo, Double precioMaximo);
}
