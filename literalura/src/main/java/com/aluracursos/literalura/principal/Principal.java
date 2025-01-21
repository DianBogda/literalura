package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private List<Libro> librosEncontrados;
    private Optional<DatosLibro> libroObtenido;
    private List<Autor> autoresEncontrados;

    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n-------------
                    1.- Buscar libro por título.
                    2.- Buscar libro por nombre de autor.
                    3.- Listar libros registrados.
                    4.- Mostrar autor por libro.
                    5.- Listar autores.
                    6.- Listar autores vivos en un determinado año.
                    7.- Listar libros por idioma.
                    8.- Top 10 de libros más descargados.
                    9.- Algunas estadísticas relacionadas con los libros.
                    10.- Autores ordenados por edad.
                    11.- Autores ordenados por año de muerte.
                    
                    0 - Salir.
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    String datoAEscribir = "libro";
                    buscarLibro(datoAEscribir);
                    //buscarDatosAutorPorLibro(); //En caso que quisiera hacer el ejercicio individualmente...
                    break;
                case 2:
                    buscarLibroPorNombreDeAutor();
                    break;
                case 3:
                   mostrarLibros();
                    break;
                case 4:
                    mostrarAutoresPorLibro();
                    break;
                case 5:
                    mostrarAutores();
                    break;
                case 6:
                    System.out.println("Escriba el año para saber que autores estaban vivos en ese periodo: ");
                    var anio = teclado.nextInt();
                    teclado.nextLine();
                    elegirAutoresVivosEnDeterminadoAnio(anio);
                    break;
                case 7:
                    var idiomasMenu = """
                            Escriba la sigla del idioma de los libros a buscar:
                            es -> español
                            en -> inglés
                            it -> italiano
                            fr -> francés
                            pt -> portugués
                            ca -> catalán
                            """;
                    System.out.println(idiomasMenu);
                    var idioma = teclado.nextLine();
                    elegirLibrosPorIdioma(idioma);
                    //buscarLibrosPorIdioma(idioma); //Para buscar en la API...
                    break;
                case 8:
                    top10LibrosMasDescargados();
                    break;
                case 9:
                    estadisticasDeLibros();
                    break;
                case 10:
                    autoresOrdenadosPorEdad();
                    break;
                case 11:
                    autoresOrdenadosPorAnioMuerte();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...\n");
                    teclado.close();
                    System.exit(0);  //Cierra la aplicación completamente
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private ResultadoDatosLibro obtenerDatosLibro(String datoAEscribir) {
        System.out.println("Escriba el nombre del " + datoAEscribir + " que desea buscar: ");
        var nombreABuscar = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreABuscar.replace(" ", "%20"));
        ResultadoDatosLibro datosLibro = conversor.obtenerDatos(json, ResultadoDatosLibro.class);
        return datosLibro;
    }

    private void buscarLibro(String libro) {
        ResultadoDatosLibro datos = obtenerDatosLibro(libro);

        libroObtenido = datos.libros().stream()
                .findFirst();

        if (libroObtenido.isPresent()) {
            DatosLibro libroEncontrado = libroObtenido.get();
            Libro nuevoLibro = new Libro(libroEncontrado);

            //Filtrar dentro de la base de datos para verificar que no exista el libro previamente.
            librosEncontrados = libroRepository.findAll().stream()
                    .filter(l -> l.getTitulo().equals(nuevoLibro.getTitulo()))
                    .toList();

            if (librosEncontrados.isEmpty()) {
                libroRepository.save(nuevoLibro);
                System.out.println("\nLibro: '" + nuevoLibro.getTitulo() +  "' encontrado y guardado.\n");

                //Se agrega el autor del libro respectivo.
                buscarDatosAutorPorLibro(datos, nuevoLibro);
            } else {
                System.out.println("\nEl libro que está buscando ya existe en la base de datos: '" + nuevoLibro.getTitulo() + "'.");
            }
        } else {
            System.out.println("\nNo se ha encontrado ningún libro que coincida con la búsqueda.");
        }
    }

    private void buscarLibroPorNombreDeAutor() {
        String autor = "autor del libro";
        buscarLibro(autor);
    }

    private void buscarDatosAutorPorLibro(ResultadoDatosLibro datosLibro, Libro nuevoLibro) {
        var libroGuardado = nuevoLibro.getTitulo();

        //Se busca el libro en la API, para obtener datos de autor.
        libroObtenido = datosLibro.libros().stream()
                .filter(dl -> dl.titulo().equals(libroGuardado))
                .findFirst();

        if (libroObtenido.isPresent()) {
            List<Autor> autores = libroObtenido.get().nombreAutor().stream()
                    .map(a -> {
                        Autor autor = new Autor(a.nombre(), a.anioNacimiento(), a.anioMuerte());
                        autor.setLibro(nuevoLibro);
                        return autor;
                    })
                    .toList();

            nuevoLibro.setAutores(autores);
            libroRepository.save(nuevoLibro);

            System.out.println("\nAutor: '" + nuevoLibro.getNombreAutor() + "' agregado correctamente.");
        }
    }

    /*//Esta opción se genera si hago una búsqueda e ingreso al autor individual...
    private void buscarDatosAutorPorLibro() {
        mostrarLibros();
        System.out.println("\nEscribe parte del nombre del libro para obtener los datos de su autor: ");
        var nombreLibro = teclado.nextLine().toLowerCase(); //Convertir a minúsculas para evitar problemas de mayúsculas.

        Optional<Libro> libroObtenido = libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(nombreLibro))
                .findFirst();

        if (libroObtenido.isPresent()) {
            var libroEncontrado = libroObtenido.get();

            //Consultar la API con la búsqueda del libro.
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
            ResultadoDatosLibro datosLibro = conversor.obtenerDatos(json, ResultadoDatosLibro.class);

            //Filtrar la respuesta para encontrar el libro correcto dentro de la API.
            Optional<DatosLibro> libroCorrecto = datosLibro.libros().stream()
                    .filter(dl -> dl.titulo().toLowerCase().contains(nombreLibro))  //Filtramos por coincidencia parcial
                    .findFirst();

            if (libroCorrecto.isPresent()) {
                List<Autor> autores = libroCorrecto.get().nombreAutor().stream()
                        .map(a -> {
                            Autor autor = new Autor(a.nombre(), a.anioNacimiento(), a.anioMuerte());
                            autor.setLibro(libroEncontrado);
                            return autor;
                        })
                        .toList();

                libroEncontrado.setAutores(autores);
                libroRepository.save(libroEncontrado);

                System.out.println("Autor agregado correctamente.\n");
            } else {
                System.out.println("No se encontró un libro coincidente en la API.\n");
            }
        } else {
            System.out.println("No hay libros en la base de datos que coincidan con la búsqueda.\n");
        }
    }*/

    private void mostrarLibros() {
        librosEncontrados = libroRepository.findAll();
        System.out.println("\n------LIBROS------");
        librosEncontrados.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void mostrarAutoresPorLibro() {
        System.out.println("Escribe el libro que estás buscando: ");
        var tituloLibro = teclado.nextLine().toLowerCase();

        Optional<Libro> libroExistente = librosEncontrados.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(tituloLibro))
                .findFirst();

        if (libroExistente.isPresent()) {
            Long libroId = libroExistente.get().getId();

            autoresEncontrados = libroRepository.autorPorTituloLibro(libroId);

            if (autoresEncontrados.isEmpty()) {
                System.out.println("\nNo se encontraron autores para este libro.");
            } else {
                autoresEncontrados.forEach(a ->
                        System.out.printf("""
                                \n-------------
                                - Título del libro: %s.
                                - Nombre del Autor: %s.
                                - Año de Nacimiento: %d.
                                - Año de Muerte: %d.
                                -------------
                                """,
                                a.getLibro().getTitulo(), a.getNombre(), a.getAnioNacimiento(), a.getAnioMuerte())
                );
            }
        } else {
            System.out.println("\nNo se encontró un libro con ese título.");
        }
    }

    private void mostrarAutores() {
        autoresEncontrados = libroRepository.autores();
        System.out.println("\n------AUTORES------");
        autoresEncontrados.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void elegirAutoresVivosEnDeterminadoAnio(int anio) {
        autoresEncontrados = libroRepository.autoresVivosEnDeterminadoAnio(anio);
        if (autoresEncontrados.isEmpty()) {
            System.out.println("\nNo existen autores vivos en ese tiempo.");
        } else {
            autoresEncontrados.forEach(a ->
                    System.out.printf("""
                                \n-------------
                                - Nombre del Autor: %s.
                                - Año de Nacimiento: %d.
                                - Año de Muerte: %d.
                                - Título del libro: %s.
                                -------------
                                """,
                            a.getNombre(), a.getAnioNacimiento(), a.getAnioMuerte(), a.getLibro().getTitulo())
            );
        }
    }

    private void elegirLibrosPorIdioma(String idioma) {
        System.out.println("\n---Idioma elegido: '" + idioma + "'---\n");

        var librosFiltrados = libroRepository.findAll().stream()
                .filter(libro -> libro.getIdiomas().contains(idioma))
                .toList();

        if (librosFiltrados.isEmpty()) {
            System.out.println("\nNo existe ningún libro en el idioma elegido.");
        } else {
            librosFiltrados.forEach(l->
                    System.out.printf("""
                                \n-------------
                                - Título: %s.
                                - Nombre del Autor: %s.
                                - Idioma: %s.
                                - Número de descargas: %.1f.
                                -------------
                                """,
                            l.getTitulo(), l.getNombreAutor(), String.join(", ", l.getIdiomas()), l.getNumeroDeDescargas())
            );
        }
    }

    /*//Ésto es para buscar libros en la API.
    private void buscarLibrosPorIdioma(String idioma) {
        System.out.println("---Idioma elegido: '" + idioma + "'---\n");

        var json = consumoAPI.obtenerDatos(URL_BASE + "?languages=" + idioma);
        ResultadoDatosLibro datosLibro = conversor.obtenerDatos(json, ResultadoDatosLibro.class);
        var datosLibroIdioma = datosLibro.libros();

        if (datosLibroIdioma.isEmpty()) {
            System.out.println("No existe ningún libro en el idioma elegido.\n");
        } else {
            datosLibroIdioma.stream()
                    .sorted(Comparator.comparing(DatosLibro::titulo))
                    .forEach(System.out::println);
        }
    }*/

    private void top10LibrosMasDescargados() {
        librosEncontrados = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        System.out.println("\n------Top 10 de libros más descargados------\n");
        librosEncontrados.forEach(l ->
                System.out.println("* Libro: " + l.getTitulo() + " / " + "* Número de descargas: " + l.getNumeroDeDescargas())
        );
    }

    private void estadisticasDeLibros() {
        librosEncontrados = libroRepository.findAll();
        DoubleSummaryStatistics libroMedidos = librosEncontrados.stream()
                .filter(l -> l.getNumeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));

        var datosEstadisticos = """
                \n------Algunas métricas generales------
                
                - Media de descargas: %.1f.
                - Número mayor de descargas: %.1f.
                - Número menor de descargas: %.1f.
                - Número de registros contados: %d.
                """.formatted(
                        libroMedidos.getAverage(), libroMedidos.getMax(), libroMedidos.getMin(), libroMedidos.getCount()
                );
        System.out.println(datosEstadisticos);
    }

    private List<Autor> listaAutores() {
        var listaAutores = libroRepository.autores().stream()
                .toList();
        return listaAutores;
    }

    private void autoresOrdenadosPorEdad() {
        LocalDateTime hoy = LocalDateTime.now();
        var anioActual = hoy.getYear();
        int edad;

        var listaAutores = listaAutores().stream()
                .sorted(Comparator.comparing(Autor::getAnioNacimiento))
                .toList();

        var nombreAutores = listaAutores.stream()
                .map(Autor::getNombre)
                .toArray();

        var edadAutores = listaAutores.stream()
                .flatMapToInt(a -> IntStream.of(a.getAnioNacimiento()))
                .toArray();

        System.out.println("\n------Autores ordenados por la edad que tendrían este año " + anioActual + "------\n");
        for (int i = 0; i < listaAutores.size(); i++) {
           edad = anioActual - edadAutores[i];
            System.out.println("* Autor: " + nombreAutores[i] + " / * Edad: " + edad);
        }
    }

    private void autoresOrdenadosPorAnioMuerte() {
        var listaAutores = listaAutores().stream()
                .sorted(Comparator.comparing(Autor::getAnioMuerte).reversed())
                .toList();

        var nombreAutores = listaAutores.stream()
                .map(Autor::getNombre)
                .toArray();

        var muerteAutores = listaAutores.stream()
                .map(Autor::getAnioMuerte)
                .toArray();

        System.out.println("\n------Autores ordenados por año de fallecimiento------\n");
        for (int i = 0; i < listaAutores.size(); i++) {
            System.out.println("* Autor: " + nombreAutores[i] + " / * Año de muerte: " + muerteAutores[i]);
        }
    }
}
