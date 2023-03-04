package com.besysoft.springbootejercitacion1.excepciones.EntityAdded;

public abstract class EntityAddedException extends RuntimeException {

    public EntityAddedException(String message) {
        super(message);
    }

    public EntityAddedException(String message, Throwable cause) {
        super(message, cause);
    }
}
