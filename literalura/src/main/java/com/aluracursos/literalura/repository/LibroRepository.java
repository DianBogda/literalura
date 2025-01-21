package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.LongStream;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT a FROM Autor a WHERE a.libro.id = :libroId")
    List<Autor> autorPorTituloLibro(Long libroId);

    @Query("SELECT a FROM Autor a")
    List<Autor> autores();

    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioMuerte IS NULL OR a.anioMuerte > :anio)")
    List<Autor> autoresVivosEnDeterminadoAnio(int anio);
}
