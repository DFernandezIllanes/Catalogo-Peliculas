package com.besysoft.springbootejercitacion1.excepciones.EntityExist;

public class PersonajeExistException extends EntityExistException {

    public PersonajeExistException(String message) {
        super(message);
    }

    public PersonajeExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
