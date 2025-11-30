package com.ejemplo.articulos.repository;
import com.ejemplo.articulos.model.Articulo;
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
    

}
