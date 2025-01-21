package com.aluracursos.literalura.controller;

import com.aluracursos.literalura.dto.LibroDTO;
import com.aluracursos.literalura.model.DatosLibro;
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

}
