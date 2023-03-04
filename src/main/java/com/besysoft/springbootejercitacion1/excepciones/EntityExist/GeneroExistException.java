package com.besysoft.springbootejercitacion1.excepciones.EntityExist;

public class GeneroExistException extends EntityExistException {

    public GeneroExistException(String message) {
        super(message);
    }

    public GeneroExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
