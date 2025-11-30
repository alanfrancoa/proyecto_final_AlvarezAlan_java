package com.ejemplo.articulos.controller;

import com.ejemplo.articulos.model.Articulo;
import com.ejemplo.articulos.service.ArticuloService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*") // Permitir solicitudes desde cualquier origen
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/articulos") // Ruta base para los endpoints de artículos
public class ArticuloController {

    private final ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping
    public List<Articulo> listar() {
        return articuloService.listarArticulos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id)
                .map(ResponseEntity::ok) // 200 OK
                // metodo de la clase Optional<T>
                // Optional<T> tiene un metodo map que recibe una funcion que transforma el
                // valor contenido en el Optional si este esta presente
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // metodo para que devuelva 400 en caso de bad request

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Articulo articulo) {
        if (articulo.getNombre() == null || articulo.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("No puede estar vacio el nombre"); // 400 Bad Request
        }
        if (articulo.getPrecio() < 0 || articulo.getPrecio() == null) {
            return ResponseEntity.badRequest().body("El precio no puede ser negativo o nulo"); // 400 Bad Request
        }
        Articulo articuloCreado = articuloService.guardarArticulo(articulo);
        return ResponseEntity.status(201).body(articuloCreado); // 201 Created
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Articulo articulo) {
        if (!articuloService.obtenerArticuloPorId(id).isPresent()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        if (articulo.getNombre() == null || articulo.getNombre().isEmpty()) {
            return ResponseEntity.badRequest().body("No puede estar vacio el nombre"); // 400 Bad Request
        }
        if (articulo.getPrecio() < 0 || articulo.getPrecio() == null) {
            return ResponseEntity.badRequest().body("El precio no puede ser negativo o nulo"); // 400 Bad Request
        }
        Articulo articuloActualizado = articuloService.actualizarArticulo(id, articulo);
        return ResponseEntity.ok(articuloActualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!articuloService.obtenerArticuloPorId(id).isPresent()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
