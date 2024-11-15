package com.alejandro.literalura.Implementations;

import com.alejandro.literalura.Entities.AutorDTO;
import com.alejandro.literalura.Entities.BookDTO;
import com.alejandro.literalura.Entities.Models.Author;
import com.alejandro.literalura.Entities.Models.Books;
import com.alejandro.literalura.Entities.ResultsDTO;
import com.alejandro.literalura.Exceptions.EntityExistException;
import com.alejandro.literalura.Exceptions.HttpRequestException;
import com.alejandro.literalura.Exceptions.ParseJsonException;
import com.alejandro.literalura.Mappers.AutorMapper;
import com.alejandro.literalura.Mappers.BookMapper;
import com.alejandro.literalura.Mappers.JsonMapper;
import com.alejandro.literalura.Repositories.AutorRepository;
import com.alejandro.literalura.Repositories.BookRepository;
import com.alejandro.literalura.Services.APIService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LogicService {

    private final APIService apiService;
    private final JsonMapper mapper;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AutorMapper autorMapper;
    private final AutorRepository autorRepository;
    private final Scanner entry;

    private static final Map<String, String> IDIOMA_CODIGOS = Map.of(
            "ingles", "en",
            "inglés", "en",
            "frances", "fr",
            "francés", "fr",
            "aleman", "al",
            "alemán", "al",
            "español", "es",
            "espaniol", "es"
    );

    private ResultsDTO consultar(String consulta){
        try{
            var json = apiService.httpResponse(consulta);
            return mapper.jsonMap(json, ResultsDTO.class);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("\nNo se ha obtenido ninguno elemento de resultados, verifique su consulta a la API");
        }
        catch(ParseJsonException | HttpRequestException | IOException | InterruptedException e){
            System.out.println("\nError de consulta! " + e.getMessage());
        }
        return null;
    }

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


    private void muestraResultado(Object object){
        var imprimeResultado =
                object instanceof AutorDTO ?
                        String.format("""
                                \nnombre del autor = %s
                                Fecha de nacimiento = %d
                                Fecha de muerte (si aplica) = %d
                                """,
                                ((AutorDTO) object).nombreAutor(),
                                ((AutorDTO) object).fechaNacimiento(),
                                ((AutorDTO) object).fechaNacimiento())
                        :
                        object instanceof BookDTO ?
                                String.format("""
                                        \nTitulo del libro = %s
                                        autor(es) = %s
                                        Idiomas = %s
                                        Ejemplares descargados = %d
                                        """,
                                        ((BookDTO) object).titulo(),
                                        ((BookDTO) object).autor().get(0).nombreAutor(),
                                        Arrays.toString(((BookDTO) object).idiomas().toArray()),
                                        ((BookDTO) object).ejemplaresDescargados()
                                )
                        :
                                "Sin resultado";
        System.out.println(imprimeResultado);
    }

    public void consultarLibro(){
        System.out.print("¿Qué libro desea buscar?: ");
        var search = entry.nextLine();
        Optional<ResultsDTO> result = Optional.ofNullable(consultar(search));
        result.ifPresentOrElse(consultResult -> {
            try {
                BookDTO book = consultResult.listaLibrosEcontrados().get(0);
                muestraResultado(book);
                Books entityBook = bookMapper.DTOtoEntity(book);
                validarEntidadEnDB(entityBook);
                bookRepository.save(entityBook);
            } catch (IndexOutOfBoundsException | EntityExistException e) {
                System.out.println("\nAlgo ocurrió: " + e.getMessage());
            }
        }, () -> System.out.println("No se encontraron resultados para el libro especificado."));
    }

    public void consultarAutor(){
        System.out.print("¿Qué autor desea buscar?: ");
        var search = entry.nextLine();
        Optional<ResultsDTO> result = Optional.ofNullable(consultar(search));
        result.ifPresentOrElse(consultResult -> {
            try {
                AutorDTO autor = consultResult.listaLibrosEcontrados().get(0).autor().get(0);
                muestraResultado(autor);
                Author autorEntity = autorMapper.DTOtoEntity(autor);
                validarEntidadEnDB(autorEntity);
                autorRepository.save(autorEntity);
            } catch (IndexOutOfBoundsException | EntityExistException e) {
                System.out.println("\nAlgo ocurrió: " + e.getMessage());
            }
        }, () -> System.out.println("No se encontraron resultados para el autor especificado."));
    }

    public void listaLibrosConsultados(){
       List<String> librosConsultados = bookRepository.findAllTitulo();
       if(librosConsultados.isEmpty()){
           System.out.println("\nNo se ha encontrado libros en la base de datos");
       }else{
           System.out.println("\nLista de libros consultados en la API\n");
           librosConsultados.forEach(System.out::println);
       }
    }

    public void listaAutoresConsultados(){
        List<String> autoresConsultados = autorRepository.findAllNombreAutor();
        if(autoresConsultados.isEmpty()){
            System.out.println("\nNo se ha encontrado autores en la base de datos");
        }else{
            System.out.println("\nLista de autores según libros o autores consultados en la API\n");
            autoresConsultados.forEach(System.out::println);
        }
    }

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

    public void cantidadDeLibrosSegunIdioma(String search){
        String codigoIdioma = IDIOMA_CODIGOS.get(search);
        if (codigoIdioma != null) {
            System.out.println("Cantidad de libros en " + search + ": " + bookRepository.countByIdioma(codigoIdioma));
        } else {
            System.out.println("Idioma no reconocido. Intente nuevamente.");
        }
    }
}