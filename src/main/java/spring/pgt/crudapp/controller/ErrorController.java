package spring.pgt.crudapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import spring.pgt.crudapp.model.WebResponse;

@RestControllerAdvice
public class ErrorController {

    // MENANGANI ERROR DARI CONSTRAINT / BATASAN YANG DIBUAT
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> batasanException(ConstraintViolationException exceptionNya) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder().errors(exceptionNya.getMessage()).build());
    }

    // MENANGANI ERROR DARI SERVER
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> serverException(ResponseStatusException exceptionNya) {
        return ResponseEntity
                .status(exceptionNya.getStatusCode())
                .body(WebResponse.<String>builder().errors(exceptionNya.getReason()).build());
    }
}
