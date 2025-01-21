package com.aluracursos.literalura.controller;

import com.aluracursos.literalura.dto.AutorDTO;
import com.aluracursos.literalura.dto.LibroDTO;
import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService servicio;

    @GetMapping
    public List<LibroDTO> obtenerTodosLosLibros() {
        return servicio.obtenerTodosLosLibros();
    }

    @GetMapping("{id}")
    public LibroDTO obtenerLibroPorId(@PathVariable Long id) {
        return servicio.obtenerLibroPorId(id);
    }

    @GetMapping("/{id}/autor")
    public List<AutorDTO> obtenerAutorPorLibroId(@PathVariable Long id) {
        return servicio.obtenerAutorPorLibroId(id);
    }

    @GetMapping("/autores")
    public List<AutorDTO> obtenerTodosLosAutores() {
        return servicio.obtenerTodosLosAutores();
    }

    @GetMapping("/top10")
    public List<LibroDTO> obtenerTop10LibrosDescargados() {
        return servicio.obtenerTop10LibrosDescargados();
    }

    @GetMapping("/idioma/{idioma}")
    public List<LibroDTO> obtenerLibroPorIdioma(@PathVariable String idioma) {
        return servicio.obtenerLibroPorIdioma(idioma);
    }

    @GetMapping("autores/{anio}")
    public List<AutorDTO> obtobtenerAutoresVivosEnDeterminadoAnio(@PathVariable int anio) {
        return servicio.obtenerAutoresVivosEnDeterminadoAnio(anio);
    }
}
