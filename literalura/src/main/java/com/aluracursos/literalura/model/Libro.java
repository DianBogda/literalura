package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String nombreAutor;
    private List<String> idiomas;
    private double numeroDeDescargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = datosLibro.nombreAutor().stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setLibro(this));
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "\n------LIBRO------\n" +
                "- Título: " + titulo + ".\n" +
                "- Autor: " + nombreAutor + ".\n" +
                "- Idioma: " + String.join(", ", idiomas) + ".\n" + //Conversión de lista a string, separado por comas.
                "- Número de descargas: " + numeroDeDescargas + ".\n" +
                "-------------";
    }
}
