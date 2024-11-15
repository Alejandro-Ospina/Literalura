package com.alejandro.literalura.Exceptions;

public class HttpRequestException extends Exception{
    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
