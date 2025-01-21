package com.aluracursos.literalura.service;

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
    private LibroRepository repository;

    public List<LibroDTO> convierteDatos(List<Libro> libros){
        return libros.stream()
                .map(l -> new LibroDTO(l.getId(), l.getTitulo(), l.getAutor(), l.getIdiomas(), l.getNumeroDeDescargas()))
                .collect(Collectors.toList());
    }

    public List<LibroDTO> obtenerTodosLosLibros() {
        return convierteDatos(repository.findAll());
    }

    public LibroDTO obtenerLibroPorId(Long id) {
        Optional<Libro> libro = repository.findById(id);
        if (libro.isPresent()){
            Libro l = libro.get();
            return new LibroDTO(l.getId(), l.getTitulo(), l.getAutor(), l.getIdiomas(), l.getNumeroDeDescargas());
        }
        return null;
    }
}
