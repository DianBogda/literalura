package com.aluracursos.literalura.service;

import com.aluracursos.literalura.dto.AutorDTO;
import com.aluracursos.literalura.dto.LibroDTO;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<LibroDTO> convierteDatos(List<Libro> libros){
        return libros.stream()
                .map(l ->
                        new LibroDTO(
                                l.getId(),
                                l.getTitulo(),
                                l.getNombreAutor(),
                                l.getIdiomas(),
                                l.getNumeroDeDescargas()
                        )
                )
                .collect(Collectors.toList());
    }

    public List<LibroDTO> obtenerTodosLosLibros() {
        return convierteDatos(libroRepository.findAll());
    }

    public LibroDTO obtenerLibroPorId(Long id) {
        Optional<Libro> libro = libroRepository.findById(id);
        if (libro.isPresent()){
            Libro l = libro.get();
            return new LibroDTO(
                    l.getId(),
                    l.getTitulo(),
                    l.getNombreAutor(),
                    l.getIdiomas(),
                    l.getNumeroDeDescargas()
            );
        }
        return null;
    }

    public List<AutorDTO> obtenerAutorPorLibroId(Long id) {
        Optional<Libro> libro = libroRepository.findById(id);
        if (libro.isPresent()) {
            Libro l = libro.get();
            return l.getAutores().stream()
                    .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioMuerte()))
                    .toList();
        }
        return null;
    }

    public List<AutorDTO> obtenerTodosLosAutores() {
        return libroRepository.autores().stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioMuerte()))
                .toList();
    }

    public List<LibroDTO> obtenerTop10LibrosDescargados() {
        return convierteDatos(libroRepository.findTop10ByOrderByNumeroDeDescargasDesc());
    }

    public List<LibroDTO> obtenerLibroPorIdioma(String idioma) {
        return libroRepository.findAll().stream()
                .filter(l -> l.getIdiomas().contains(idioma))
                .map(nl -> new LibroDTO(nl.getId(), nl.getTitulo(), nl.getNombreAutor(), nl.getIdiomas(), nl.getNumeroDeDescargas()))
                .toList();
    }

    public List<AutorDTO> obtenerAutoresVivosEnDeterminadoAnio(int anio) {
        return libroRepository.autoresVivosEnDeterminadoAnio(anio).stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioMuerte()))
                .toList();
    }
}
