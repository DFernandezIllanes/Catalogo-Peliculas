package com.besysoft.springbootejercitacion1.excepciones.EntityNotFound;

public class PeliculaNotFoundException extends EntityNotFoundException {

    public PeliculaNotFoundException(String message) {
        super(message);
    }

    public PeliculaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
