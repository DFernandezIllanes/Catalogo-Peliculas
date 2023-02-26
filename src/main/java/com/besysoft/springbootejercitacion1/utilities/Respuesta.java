package com.besysoft.springbootejercitacion1.utilities;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Respuesta{

    private static Map<String, Object> mensajeBody = new HashMap<>();
    private static HttpHeaders headers = new HttpHeaders();

    static {
        headers.set("app-info", "nombre@dominio.com");
    }

    public static void addHeader(String string1, String string2) {
        headers.set(string1, string2);
    }

    public static ResponseEntity<?> generar(Boolean bool, Object object) {

        if(!bool) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", object);

            return ResponseEntity.badRequest().headers(headers).body(mensajeBody);
        }
        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", object);

        return new ResponseEntity<>(mensajeBody, headers, HttpStatus.OK);

    }

    public static ResponseEntity<?> generar(HttpStatus httpStatus, Boolean bool, Object object) {

        if(!bool) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", object);

            return ResponseEntity.badRequest().headers(headers).body(mensajeBody);
        }
        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", object);

        return new ResponseEntity<>(mensajeBody, headers, httpStatus);

    }
}
