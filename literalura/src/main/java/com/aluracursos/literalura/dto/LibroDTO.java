package com.aluracursos.literalura.dto;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosAutor;

import java.util.List;

public record LibroDTO(
        Long Id,
        String titulo,
        List<DatosAutor> autores,
        List<String> idiomas,
        double numDescargas
    ) {
}
