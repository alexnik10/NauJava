package ru.alex.NauJava.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alex.NauJava.exceptions.ContactNotFoundException;
import ru.alex.NauJava.exceptions.NotFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception e) {
        return ErrorResponse.create(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleContactNotFoundException(NotFoundException e) {
        return ErrorResponse.create(e);
    }

    public static class ErrorResponse {
        private String message;

        private ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static ErrorResponse create(Throwable e) {
            return new ErrorResponse(e.getMessage());
        }

        public static ErrorResponse create(String message) {
            return new ErrorResponse(message);
        }
    }
}
