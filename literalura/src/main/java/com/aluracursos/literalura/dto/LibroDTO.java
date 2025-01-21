package com.aluracursos.literalura.dto;

import java.util.List;

public record LibroDTO(
        Long Id,
        String titulo,
        String nombreAutor,
        List<String> idiomas,
        double numDescargas
    ) {
}
