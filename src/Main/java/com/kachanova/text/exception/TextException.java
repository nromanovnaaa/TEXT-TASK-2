package com.kachanova.text.exception;

// Custom unchecked exception for text processing errors.
public class TextException extends RuntimeException {

    public TextException(String message) {
        super(message);
    }

    public TextException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextException(Throwable cause) {
        super(cause);
    }
}