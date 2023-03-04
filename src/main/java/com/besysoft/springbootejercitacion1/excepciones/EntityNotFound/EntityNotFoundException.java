package com.besysoft.springbootejercitacion1.excepciones.EntityNotFound;

public abstract class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
