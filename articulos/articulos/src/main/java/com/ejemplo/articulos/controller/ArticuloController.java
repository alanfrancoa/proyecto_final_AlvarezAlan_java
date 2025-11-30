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

    // Inyección de dependencia del servicio de artículos
    private final ArticuloService articuloService;

    // Constructor con inyección de dependencia
    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    // Endpoint para listar todos los artículos
    @GetMapping
    public List<Articulo> listar() {
        return articuloService.listarArticulos();
    }

    // Endpoint para obtener un artículo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id)
                .map(ResponseEntity::ok) // 200 OK
                // metodo de la clase Optional<T>
                // Optional<T> tiene un metodo map que recibe una funcion que transforma el
                // valor contenido en el Optional si este esta presente
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // Endpoint para crear un nuevo artículo
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

    // Endpoint para actualizar un artículo existente
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

    // Endpoint para eliminar un artículo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!articuloService.obtenerArticuloPorId(id).isPresent()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Endpoints de busqueda con filtros
    // URL Base: /api/articulos/buscar
    // Ejemplos de uso:
    // /api/articulos/buscar?nombre=telefono
    // /api/articulos/buscar?precioMin=100&precioMax=500
    // /api/articulos/buscar?nombre=telefono&precioMin=100&precioMax=500
    @GetMapping("/buscar")
    public List<Articulo> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        // inicio del metodo buscar

        // Caso 1: Buscar por nombre, precioMin y precioMax
        if (nombre != null && precioMin != null && precioMax != null) {
            return articuloService.buscarPorNombreYPrecioEntre(nombre, precioMin, precioMax);
        }

        // Caso 2: Buscar por nombre solamente
        if (nombre != null && precioMin == null && precioMax == null) {
            return articuloService.buscarPorNombre(nombre);
        }
        // Caso 3: Buscar por rango de precio solamente
        if (nombre == null && precioMin != null && precioMax != null) {
            return articuloService.buscarPorPrecioEntre(precioMin, precioMax);
        }

        // Caso 4: solo precioMin
        if (nombre == null && precioMin != null && precioMax == null) {
            return articuloService.buscarPorPrecioMinimo(precioMin);
        }

        // Caso 5: solo precioMax
        if (nombre == null && precioMin == null && precioMax != null) {
            return articuloService.buscarPorPrecioMaximo(precioMax);
        }

        // Caso 6: no se proporcionan filtros, devolver todos los artículos
        return articuloService.listarArticulos();

    }

}
