package com.alejandro.literalura.Exceptions;

public class ParseJsonException extends Exception {
    public ParseJsonException(String message) {
        super(message);
    }

    public ParseJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
