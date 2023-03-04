package com.besysoft.springbootejercitacion1.excepciones.EntityNotFound;

public class PersonajeNotFoundException extends EntityNotFoundException {

    public PersonajeNotFoundException(String message) {
        super(message);
    }

    public PersonajeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
