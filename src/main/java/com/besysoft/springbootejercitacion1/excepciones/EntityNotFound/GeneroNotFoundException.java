package com.besysoft.springbootejercitacion1.excepciones.EntityNotFound;

public class GeneroNotFoundException extends EntityNotFoundException {

    public GeneroNotFoundException(String message) {
        super(message);
    }

    public GeneroNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
