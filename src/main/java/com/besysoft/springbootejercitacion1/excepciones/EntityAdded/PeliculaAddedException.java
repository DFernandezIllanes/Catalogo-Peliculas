package com.besysoft.springbootejercitacion1.excepciones.EntityAdded;

public class PeliculaAddedException extends EntityAddedException {

    public PeliculaAddedException(String message) {
        super(message);
    }

    public PeliculaAddedException(String message, Throwable cause) {
        super(message, cause);
    }
}
