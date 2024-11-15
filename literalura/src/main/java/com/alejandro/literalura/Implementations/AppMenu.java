package com.alejandro.literalura.Implementations;

import com.alejandro.literalura.Mappers.AutorMapper;
import com.alejandro.literalura.Mappers.BookMapper;
import com.alejandro.literalura.Mappers.JsonMapper;
import com.alejandro.literalura.Repositories.AutorRepository;
import com.alejandro.literalura.Repositories.BookRepository;
import com.alejandro.literalura.Services.APIService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public final class AppMenu {

    private final Scanner entry;
    private final LogicService logicService;

    // Constantes para opciones del menú
    private static final int CONSULTAR_LIBROS = 1;
    private static final int CONSULTAR_AUTORES = 2;
    private static final int SALIR = 3;
    private static final int VOLVER_AL_MENU = 4;

    // Constantes para opciones dentro de cada submenú
    private static final int CONSULTAR_LIBRO_API = 1;
    private static final int CONSULTAR_AUTOR_API = 1;
    private static final int CONSULTAR_LIBROS_DB = 2;
    private static final int CONSULTAR_AUTOR_DB = 2;
    private static final int CONSULTAR_LIBROS_IDIOMA = 3;
    private static final int CONSULTAR_AUTORES_VIGENTES = 3;
    private static final int VOLVER_MENU_LIBROS = 5;

    public void runApp() {
        int opcionMenu;
        while (true) {
            mostrarMenuPrincipal();
            System.out.print("Ingrese una opción: ");
            opcionMenu = obtenerOpcionMenu();
            entry.nextLine();
            switch (opcionMenu) {
                case CONSULTAR_LIBROS:
                    procesarMenuLibros();
                    break;
                case CONSULTAR_AUTORES:
                    procesarMenuAutores();
                    break;
                case SALIR:
                    System.out.println("Se ha finalizado el uso de la app!\n");
                    return;
                default:
                    opcionNoValida();
                    break;
            }
        }
    }

    private int obtenerOpcionMenu() {
        while (true) {
            try {
                return entry.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada de datos inválida!\n");
                entry.nextLine();
            }
        }
    }

    private void mostrarMenuPrincipal() {
        var menu = """ 
                \nBienvenido a la App de consulta de libros en la API Gutendex!
                A continuación se muestra el menú de opciones.
                
                Menú:
                1. Consultar libros
                2. Consultar autores
                3. Salir de la aplicación
                """;
        System.out.println(menu);
    }

    private void mostrarMenuLibros() {
        var menu = """ 
                \nA continuación se muestra el menú de libros.
                
                Menú:
                1. Consultar libro en la API por nombre
                2. Consultar libros indexados en la base de datos
                3. Consultar cantidad de libros según un idioma específico
                4. Volver al menú principal
                """;
        System.out.println(menu);
    }

    // Método para mostrar el menú de autores
    private void mostrarMenuAutores() {
        var menu = """ 
                \nA continuación se muestra el menú de autores.
                
                Menú:
                1. Consultar autor en la API por nombre
                2. Consultar autores indexados en la base de datos
                3. Consultar qué autores se encuentran vigentes según fecha específica
                4. Volver al menú principal
                """;
        System.out.println(menu);
    }

    // Método para mostrar el menú de idiomas
    private void mostrarMenuIdiomas() {
        var menu = """ 
                \nA continuación se muestra el menú de algunos idiomas disponibles para consultar la cantidad de libros
                disponibles en cada uno
                
                Menú:
                1. Consultar cantidad de libros en idioma inglés
                2. Consultar cantidad de libros en idioma francés
                3. Consultar cantidad de libros en idioma español
                4. Consultar cantidad de libros en idioma alemán
                5. Volver al menú de libros
                """;
        System.out.println(menu);
    }

    // Procesa el menú de libros
    private void procesarMenuLibros() {
        int opcionLibros;
        while (true) {
            mostrarMenuLibros();
            System.out.print("Ingrese una opción: ");
            opcionLibros = obtenerOpcionMenu();
            entry.nextLine();
            switch (opcionLibros) {
                case CONSULTAR_LIBRO_API:
                    logicService.consultarLibro();
                    break;
                case CONSULTAR_LIBROS_DB:
                    logicService.listaLibrosConsultados();
                    break;
                case CONSULTAR_LIBROS_IDIOMA:
                    procesarMenuIdiomas();
                    break;
                case VOLVER_AL_MENU:
                    return;
                default:
                    opcionNoValida();
                    break;
            }
        }
    }

    private void procesarMenuAutores() {
        int opcionAutores;
        while (true) {
            mostrarMenuAutores();
            System.out.print("Ingrese una opción: ");
            opcionAutores = obtenerOpcionMenu();
            entry.nextLine();
            switch (opcionAutores) {
                case CONSULTAR_AUTOR_API:
                    logicService.consultarAutor();
                    break;
                case CONSULTAR_AUTOR_DB:
                    logicService.listaAutoresConsultados();
                    break;
                case CONSULTAR_AUTORES_VIGENTES:
                    logicService.listaAutoresVivosHastaCiertaFecha();
                    break;
                case VOLVER_AL_MENU:
                    return;
                default:
                    opcionNoValida();
                    break;
            }
        }
    }

    private void procesarMenuIdiomas() {
        int opcionIdiomas;
        while (true) {
            mostrarMenuIdiomas();
            System.out.print("Ingrese una opción: ");
            opcionIdiomas = obtenerOpcionMenu();
            entry.nextLine();
            switch (opcionIdiomas) {
                case 1:
                    logicService.cantidadDeLibrosSegunIdioma("ingles");
                    break;
                case 2:
                    logicService.cantidadDeLibrosSegunIdioma("frances");
                    break;
                case 3:
                    logicService.cantidadDeLibrosSegunIdioma("español");
                    break;
                case 4:
                    logicService.cantidadDeLibrosSegunIdioma("aleman");
                    break;
                case VOLVER_MENU_LIBROS:
                    return;
                default:
                    opcionNoValida();
                    break;
            }
        }
    }

    private void opcionNoValida() {
        System.out.println("Opción no válida! Intenta de nuevo.\n");
    }
}
