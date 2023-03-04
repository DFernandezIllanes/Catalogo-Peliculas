package com.besysoft.springbootejercitacion1.controller.handlerException;

import com.besysoft.springbootejercitacion1.excepciones.EntityExist.EntityExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.EntityNotFoundException;
import com.besysoft.springbootejercitacion1.negocio.dto.response.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO exceptionHandler(MethodArgumentNotValidException ex) {
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        Map<String, String> detalle = new HashMap<>();
        errorList.forEach(e -> detalle.put(e.getField(), e.getDefaultMessage()));
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Validaciones", detalle);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO isExist(EntityExistException ex) {
        return new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO notFound(EntityNotFoundException ex) {
        return new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }
}
