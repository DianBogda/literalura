package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DatosAutor> autores = new ArrayList<>();
    private List<String> idiomas;
    private double numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public List<DatosAutor> getAutor() {
        return autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public Long getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "- Título: '" + titulo + '\'' +
                "- Autor: '" + autores + '\'' +
                "- Idiomas: '" + idiomas + '\'' +
                "- Número de Descargas: '" + numeroDeDescargas + '\'';
    }
}