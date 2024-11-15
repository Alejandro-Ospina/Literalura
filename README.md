# Literalura
Bienvenidos al repositorio Literalura. Este proyecto corresponde al segundo desafío del módulo backend con Alura-Oracle. A continuación, se explica el desarrollo, tecnologías y flujo de ejecución del mismo.

## Tabla de Contenido
***
* [Explicación del Proyecto](#explicación-del-proyecto)
* [Tecnologías Usadas](#tecnologías-usadas)
* [Estructura del Proyecto](#estructura-del-proyecto)
* [Flujo de Ejecución](#flujo-de-ejecución)
  * [Presentación del Menú General](#presentación-del-menú-general)
  * [Menú de Libros](#menú-de-libros)
  * [Menú de Autores](#menú-de-autores)
* [Retos de Alura](#retos-de-alura)

---

## Explicación del Proyecto
***
Literalura es una aplicación de consola diseñada para la gestión de libros y autores mediante la integración de la API externa **[Gutendex](https://gutendex.com/)** y una base de datos relacional. El propósito principal es fortalecer habilidades de backend, como el manejo de excepciones, la validación de datos, la persistencia en bases de datos y la implementación de patrones de diseño.

Este proyecto es parte del segundo desafío del módulo backend de Alura-Oracle, enfocado en fomentar buenas prácticas de desarrollo y habilidades para resolver problemas complejos mediante el uso de Java y herramientas modernas.

---

## Tecnologías Usadas
***
- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para la creación de aplicaciones backend con enfoque modular.
- **JPA (Java Persistence API)**: Para la persistencia y mapeo de datos en la base de datos.
- **MapStruct**: Para la transformación eficiente entre DTOs y entidades.
- **PostgreSQL**: Base de datos relacional.
- **API [Gutendex](https://gutendex.com/)**: Fuente de datos inicial para libros y autores.
- **Lombok**: Simplificación del código con anotaciones para getters, setters y constructores.
- **Scanner**: Entrada y salida de datos en consola.
- **Jackson**: Libreria usada para el mapeo y transformación de datos json en objetos java, y viceversa.

---

## Estructura del Proyecto
***
El proyecto está organizado en capas para garantizar una arquitectura limpia y mantenible:

- **Servicios**: Contienen la lógica del negocio.
- **Repositorios**: Acceden y manipulan los datos almacenados en la base de datos.
- **Entidades**: Representan los modelos de datos.
- **DTOs**: Intercambio de datos con la API y entre capas.
- **Mappers**: Transforman datos entre entidades y DTOs.

Ejemplo de una entidad principal, `Books`:

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Books implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idBook;
    private String titulo;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "idBook"))
    @Column(name = "language")
    private Set<String> idiomas;
    private Integer ejemplaresDescargados;

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable (
            name = "author_books",
            joinColumns = @JoinColumn(name = "idBook"),
            inverseJoinColumns = @JoinColumn(name = "idAutor")
    )
    private Set<Author> autores;
}
```
## Flujo de ejecución

El programa interactúa principalmente con el usuario a través de un menú general, que deriva en submenús según la funcionalidad deseada. A continuación, se describe el flujo:

### Presentación del Menú General

El menú principal ofrece las siguientes opciones (Se muestra una captura del menú impreso en consola)

![Captura desde 2024-11-15 15-27-00](https://github.com/user-attachments/assets/4f05f50b-f9e3-429a-8775-a789819c196d)

Como se mencionó anteriormente, dependiendo de la opción elegida, se desplegarán diferentes submenús para dar continuidad al funcionamiento de diferentes features de la app.

### Menú de Libros

Este submenú se despliega al presionar la opción `1` del [menú principal](#presentación-del-menú-general) permite buscar libros a través de la API externa. Lo anterior incluye:

![Captura desde 2024-11-15 15-32-49](https://github.com/user-attachments/assets/e1bf0d6a-c93e-4591-9ed4-f311c71e6a76)

**Si se elige opción 1 del submenú:**

Aparecerá el mensaje del libro que desea buscar en la API:

![Captura desde 2024-11-15 15-35-51](https://github.com/user-attachments/assets/18dd60f9-f213-4e7b-ac15-135077718ec7)

Dependiendo del libro que el usuario desee buscar, podría tener tres escenarios:

1. Se encuentra coincidencia y se persiste.
2. No se encuentra coincidencia, por lo cual se descarta transacción en la base de datos.
3. Se encuentra una coincidencia que previamente se había consultado, y se descarta operación en la DB.

Cabe aclarar que siempre hay un método de validación que corrobora lo que se ha ido indexando en la base de datos, en aras de evitar caer en SQLExceptions debido a las restricciones de campo de valor único que tienen las tablas.

Ejemplo del método de validación utilizado:

```java
private void validarEntidadEnDB(Object object) throws EntityExistException {
        if (object instanceof Books) {
            if (bookRepository.findByTitulo(((Books) object).getTitulo()).isPresent()) {
                throw new EntityExistException("Ya existe un libro con el nombre ingresado en la base de datos." +
                        "\nNo se guardará el resultado.");
            }
        } else if (object instanceof Author) {
            if (autorRepository.findByNombreAutor(((Author) object).getNombreAutor()).isPresent()) {
                throw new EntityExistException("Ya existe un autor con el nombre ingresado en la base de datos." +
                        "\nNo se guardará el resultado.");
            }
        }
    }
```
![Captura desde 2024-11-15 15-42-26](https://github.com/user-attachments/assets/b9991ae6-dff9-4903-a37c-e7a96945875b)

Como se aprecia, se muestra un ejemplo donde se hace una consulta de cualquier libro. La app siempre tratará de mostrar cualquier coincidencia (si es que encuentra un libro), y a pesar de que en el ejemplo lo hace, se ve claramente que ese libro ya existia en la base
de datos, por lo cual se descarta operación de persistencia en la misma, según el mensaje. 

Al finalizar, el usuario vuelve al submenú de libros, donde podrá seguir eligiendo su opción de preferencia, o volver al menú principal.

**Si elige la opción 2 del submenú:**

El usuario podrá ver una lista de todos los libros que ha consultado mediante la opción `1` tal que así:

![Captura desde 2024-11-15 15-47-58](https://github.com/user-attachments/assets/3d2d01de-73c2-4090-ac07-b015697974cf)

En este caso, se observa una lista ejemplo de 4 libros consultados.

**Si elige la opción 3 del submenú:**

Cuando el usuario opta por dicha opción, tendrá el siguiente submenú de idiomas:

![imagen](https://github.com/user-attachments/assets/f598337e-ab80-46f8-be94-43c9c30e8d0f)

En caso de elegir cualquier alternativa, podrá ver la cantidad de libros según la lista de idiomas mostrado, por ejemplo:

![Captura desde 2024-11-15 15-53-06](https://github.com/user-attachments/assets/7682b689-9d12-49f4-a170-be0ecf745610)

Asimismo, tendrá la oportunidad de devolverse en el menú para seguir realizando cualquier operación.

### Menú de autores

El submenú de autores posee la siguiente estructura:

![Captura desde 2024-11-15 15-56-14](https://github.com/user-attachments/assets/5b99798c-b3a0-4129-a77f-5eccc8194b2b)

Como se observa, este permite: buscar autores, listar autores vivos y persistir información en la base de datos. Destaca la validación de duplicados y el uso de consultas personalizadas para filtrar resultados de autores que se encuentran con vida 
hasta cierta fecha dada por el usuario:

```java
public void listaAutoresVivosHastaCiertaFecha(){
        System.out.print("Introduza la fecha para consultar autores vigentes a la fecha: ");
        int fecha;
        try{
            fecha = entry.nextInt();
            entry.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("\nEntrada inválida. Por favor ingrese un año en formato numérico.");
            return;
        }
        List<String> listaAutoresVivos = autorRepository
                .findByFechaFallecimientoIsNullOrFechaFallecimientoAfter(fecha)
                .stream()
                .map(autorMapper::EntityToDTO)
                .map(AutorDTO::nombreAutor)
                .toList();
        if(listaAutoresVivos.isEmpty()){
            System.out.println("\nNo se ha encontrado una lista de autores vivos a la fecha");
        }else{
            System.out.println("\nAutores vigentes a la fecha\n");
            listaAutoresVivos.forEach(System.out::println);
        }
    }
```

Ahora destaquemos lo que sucedería si el usuario elige cualquiera de las opciones disponibles:

**Si elige la opción 1 del submenú:**

Al igual que en el caso de libros, el usuario podrá buscar un autor en particular, y asimismo se encontrará con tres posibles escenarios:

1. Se encuentra coincidencia y se persiste.
2. No se encuentra coincidencia, por lo cual se descarta transacción en la base de datos.
3. Se encuentra una coincidencia que previamente se había consultado, y se descarta operación en la DB.

![Captura desde 2024-11-15 16-09-24](https://github.com/user-attachments/assets/8022ebaf-4269-48e7-bc17-cdcb035fbcef)

Nótese que se aplica el mismo comportamiento de la entidad libro, a la entidad autor. En este ejemplo, se aprecia que cuando se realiza consulta a la API, encuentra la coincidencia, pero como previamente ya se habia hecho una con el 
mismo autor, no se persiste la información en la base de datos.

**Si se elige la opción 2 del submenú:**

En este caso, podremos ver la lista de todos los autores que han sido consultados. Aquí se debe aclarar que por el tipo de relación bidireccional existente entre autor y libro, al consultarse un libro, el autor persiste automáticamente en la DB.

![Captura desde 2024-11-15 16-14-12](https://github.com/user-attachments/assets/eaa4a2ec-c3a2-4902-918e-0a0802e191ac)

**Si se elige la opción 3 del submenú:**

Al elegir esta alternativa, el usuario deberá ingresar un año específico. Esto arrojará una lista de todos los autores que se encuentran vivos hasta dicho año ingresado por el usuario.

![Captura desde 2024-11-15 16-17-56](https://github.com/user-attachments/assets/e65e3bbd-989e-4776-bfdf-9ea088574789)

Para finalizar, nótese que en todos los submenús está habilitada la opción de volver al menú anterior, lo cual da flexibilidad y comodidad de navegación al usuario, en aras de mantener la sesión vigente el mayor tiempo posible.

## Retos de Alura

El desafío de Alura fomenta el desarrollo de habilidades prácticas en el backend mediante tareas que simulan escenarios reales en aplicaciones empresariales. Algunos aspectos clave que se desarrollaron en este proyecto incluyen:

* Diseño de una arquitectura escalable.
* Validación de datos y manejo de excepciones.
* Persistencia eficiente de datos.
* Buenas prácticas de seguridad con respecto a información sensible dentro del código, o estructura general de un proyecto de software
* Uso de herramientas modernas como MapStruct para simplificar la transformación de objetos.
* Prácticas de depuración y solución de problemas.

Estas tareas no solo refuerzan habilidades técnicas, sino también competencias blandas como la atención al detalle, la resolución de problemas y el pensamiento crítico. Literalura es un claro ejemplo de cómo los desafíos propuestos por Alura contribuyen al crecimiento profesional en el desarrollo de software.
