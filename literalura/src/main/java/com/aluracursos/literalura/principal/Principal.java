package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;

    public Principal(LibroRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libro
                    2 - 
                    3 - 
                    4 - 
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro obtenerDatosLibro() {
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        DatosLibro datosLibro = conversor.obtenerDatos(json, DatosLibro.class);
        return datosLibro;
    }

    private void buscarLibro() {
        DatosLibro datos = obtenerDatosLibro();
        Libro libro = new Libro(datos);
        repository.save(libro);
    }

}
