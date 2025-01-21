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

    public Autor(DatosAutor datos) {
        this.nombre = datos.nombre();
        this.anioNacimiento = datos.anioNacimiento();
        this.anioMuerte = datos.anioMuerte();
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public int getAnioMuerte() {
        return anioMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public Libro getLibro() {
        return libro;
    }

    @Override
    public String toString() {
        return "- Nombre: '" + nombre + '\'' +
                "- Año de Nacimiento: '" + anioNacimiento + '\'' +
                "- Año de Fallecimiento: '" + anioMuerte + '\'' +
                "- Libro: '" + libro + '\'';
    }
}
