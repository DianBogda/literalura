package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int anioNacimiento;
    private int anioMuerte;
    @ManyToOne
    private Libro libro;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.anioMuerte = datosAutor.anioMuerte();
    }

    public Autor(String nombre, int nacimiento, int muerte) {
        this.nombre = nombre;
        this.anioNacimiento = nacimiento;
        this.anioMuerte = muerte;
    }

    public Long getId() {
        return Id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public int getAnioMuerte() {
        return anioMuerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "\n------AUTOR------\n" +
                "- Nombre: " + nombre + ".\n" +
                "- Año de Nacimiento: " + anioNacimiento + ".\n" +
                "- Año de Fallecimiento: " + anioMuerte + ".\n" +
                "- Libro: " + libro.getTitulo() + ".\n" +
                "-------------";
    }
}
