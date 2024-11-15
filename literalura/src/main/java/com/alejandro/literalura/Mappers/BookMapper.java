package com.alejandro.literalura.Mappers;

import com.alejandro.literalura.Entities.BookDTO;
import com.alejandro.literalura.Entities.Models.Books;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper extends DTOEntityMapper<Books, BookDTO>{

    @Override
    @Mapping(target = "idiomas", expression = "java(new java.util.HashSet<>(bookDTO.idiomas()))")
    @Mapping(target = "autores", source = "bookDTO.autor")
    @Mapping(target = "titulo", source = "bookDTO.titulo")
    Books DTOtoEntity(BookDTO bookDTO);

    @Override
    @Mapping(target = "idiomas", expression = "java(new java.util.ArrayList<>(books.getIdiomas()))")
    @Mapping(target = "autor", source = "autores")
    @Mapping(target = "titulo", source = "titulo")
    BookDTO EntityToDTO(Books books);
}