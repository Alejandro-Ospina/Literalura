package com.alejandro.literalura.Exceptions;

public class EntityExistException extends Exception{
    public EntityExistException(String message) {
        super(message);
    }

    public EntityExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
