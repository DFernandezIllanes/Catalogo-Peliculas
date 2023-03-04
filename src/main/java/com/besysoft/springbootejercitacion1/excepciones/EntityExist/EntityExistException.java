package com.besysoft.springbootejercitacion1.excepciones.EntityExist;

public abstract class EntityExistException extends RuntimeException {

    public EntityExistException(String message) {
        super(message);
    }

    public EntityExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
