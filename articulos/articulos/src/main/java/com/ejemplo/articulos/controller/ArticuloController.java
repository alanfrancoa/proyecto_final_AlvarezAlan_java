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
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Articulo crear(@RequestBody Articulo articulo) {
        return articuloService.guardarArticulo(articulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @RequestBody Articulo articulo) {
        if (!articuloService.obtenerArticuloPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Articulo articuloActualizado = articuloService.actualizarArticulo(id, articulo);
        return ResponseEntity.ok(articuloActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!articuloService.obtenerArticuloPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build();
    }

}
