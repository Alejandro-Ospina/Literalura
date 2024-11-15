package com.alejandro.literalura.Mappers;

import com.alejandro.literalura.Entities.AutorDTO;
import com.alejandro.literalura.Entities.Models.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AutorMapper extends DTOEntityMapper<Author, AutorDTO> {

    @Override
    @Mapping(target = "fechaFallecimiento", source = "autorDTO.fechaFallecimiento")
    Author DTOtoEntity(AutorDTO autorDTO);

    @Override
    @Mapping(target = "fechaFallecimiento", source = "author.fechaFallecimiento")
    AutorDTO EntityToDTO(Author author);
}