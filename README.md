# LiterAlura
- Proyecto realizado en Java con Spring Boot framework.
- Consumo de una API externa.
- Catálogo de Libros que ofrece una interacción textual (vía consola) con los usuarios.
- Los libros se buscan a través de una API específica => https://gutendex.com/books/

# Entorno de desarrollo Java
- Java JDK - versión 17
- Mave - versión 4
- Spring Boot - versión 3.4.1
- IDE - IntelliJ IDEA
- Proyecto en JAR

# Descripción
- Se crea el proyecto a través de Spring Initializr, con sus respectivas dependencias => Spring Data JPA, PostgreSQL Driver, Spring Web y Spring Boot Dev Tools.
- Se construye la solicitud a la API => HttpClient / HttpRequest / HttpResponse.
- Se analiza la respuesta en formato JSON.
- Se convierten los datos JSON a una clase Java con métodos => getters, setters, toString.
- Se utilizan anotaciones => @JsonIgnoreProperties, @JsonAlias => para obtener los atributos desde el cuerpot de la respuesta JSON.
- Se interactúa con el usuario a través del método Main.
- Se implementa la interfaz CommandLineRunner.
- Se utiliza la clase Scanner para capturar la entrada del usuario.
- Se consulta a la API por libros y autores.
- Se construye una base de datos, con tablas y atributos relacionados a nuestros objetos de interés: Libro y Autor.
- Se utiliza la base de datos llamando a PostgreSQL.
- Se maneja la inserción y consultas en la base de datos a través de las interfaces de repositorio.
- Se trabaja con un proyecto Spring con Spring Data JPA y JpaRepository.
- Se inserta en la base de datos el libro, con su respectivo autor, los que se relacionan a través del ID.
- Se genera una interacción dinámica con el usuario a través de la consola, a raíz de un bucle while que repite la secuencia.
- Se conecta al servidor para llamar a la base de datos a través de los respectivos servicios y controladores.
- Se generan las llamadas al localhost para consumir la base de datos creada, utilizando PostMan:
    * Buscar lista de libros en la base de datos => http://localhost:8080/libros
    * Buscar libro por Id => http://localhost:8080/libros/[1 al 20]
    * Buscar autor por Id del libro => http://localhost:8080/libros/[1 al 20]/autor
    * Buscar lista de autores en la base de datos => http://localhost:8080/libros/autores
    * Buscar lista del Top 10 de libros más descargados => http://localhost:8080/libros/top10
    * Buscar libros según el idioma deseado => http://localhost:8080/libros/idioma/[es/en/it/fr/ca]
    * Buscar autores que estén vivos en determinado año => http://localhost:8080/libros/autores/[año]
      
    * Demo de la búsqueda utilizando Insomnia => [https://drive.google.com/file/d/1P7cGgmjvohmnEEmrHCJXWPrsjlSJBTDm/view?usp=sharing](https://drive.google.com/file/d/18kkyIA-XPbI_kzUv-I4aEvCoI90epsI9/view?usp=sharing)

# Demo de la aplicación
=> [https://drive.google.com/file/d/1EMg5EosfIxpJfNPDYMMHcAwMic9Zv-as/view?usp=sharing](https://drive.google.com/file/d/1EMg5EosfIxpJfNPDYMMHcAwMic9Zv-as/view?usp=sharing)
