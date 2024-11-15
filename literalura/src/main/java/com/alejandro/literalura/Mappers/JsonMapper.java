package com.alejandro.literalura.Mappers;

import com.alejandro.literalura.Exceptions.ParseJsonException;

public interface JsonMapper {

    <T> T jsonMap (String json, Class<T> clazz) throws ParseJsonException;
}
