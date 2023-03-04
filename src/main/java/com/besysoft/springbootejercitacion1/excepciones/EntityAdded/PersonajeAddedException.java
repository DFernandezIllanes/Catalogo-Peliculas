package com.besysoft.springbootejercitacion1.excepciones.EntityAdded;

public class PersonajeAddedException extends EntityAddedException {

    public PersonajeAddedException(String message) {
        super(message);
    }

    public PersonajeAddedException(String message, Throwable cause) {
        super(message, cause);
    }
}
