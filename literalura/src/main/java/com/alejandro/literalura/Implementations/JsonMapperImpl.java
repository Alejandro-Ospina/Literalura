package com.alejandro.literalura.Implementations;

import com.alejandro.literalura.Exceptions.ParseJsonException;
import com.alejandro.literalura.Mappers.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonMapperImpl implements JsonMapper {

    private final ObjectMapper mapper;

    @Override
    public <T> T jsonMap(String json, Class<T> clazz) throws ParseJsonException{
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new ParseJsonException("Hubo un error en el parseo de datos json", e.getCause());
        }
    }
}
