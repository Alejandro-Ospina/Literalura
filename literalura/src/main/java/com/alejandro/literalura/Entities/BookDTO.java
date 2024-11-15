package com.alejandro.literalura.Entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record BookDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer ejemplaresDescargados
){
}
