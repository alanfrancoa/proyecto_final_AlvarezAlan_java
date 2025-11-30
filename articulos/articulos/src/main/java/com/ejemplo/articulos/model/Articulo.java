package com.ejemplo.articulos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "articulo") //Mapeo de la clase con la tabla articulo en la base de datos
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Incremental
    private Long id;
    private String nombre;
    private Double precio;

    //Constructores
    public Articulo() {
    }
    public Articulo(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }


}
