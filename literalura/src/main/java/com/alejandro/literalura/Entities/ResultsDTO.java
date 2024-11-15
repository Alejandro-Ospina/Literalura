package com.alejandro.literalura.Entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record ResultsDTO (
        @JsonAlias("count") Integer cantidaEncontrada,
        @JsonAlias("results") List<BookDTO> listaLibrosEcontrados
) {
}
