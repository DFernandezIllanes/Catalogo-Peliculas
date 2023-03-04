package com.besysoft.springbootejercitacion1.excepciones.EntityExist;

public class PeliculaExistException extends EntityExistException{

    public PeliculaExistException(String message) {
        super(message);
    }

    public PeliculaExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
