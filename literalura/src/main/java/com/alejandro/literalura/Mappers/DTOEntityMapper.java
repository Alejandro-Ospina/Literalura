package com.alejandro.literalura.Mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

public interface DTOEntityMapper<Entity, DTOEntity> {

    Entity DTOtoEntity (DTOEntity dto);
    DTOEntity EntityToDTO (Entity entity);

}
