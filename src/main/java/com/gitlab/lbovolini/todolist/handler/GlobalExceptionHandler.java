package com.gitlab.lbovolini.todolist.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    static class ApiError {
        private final Date timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final String path;
        private final List<Error> errors;

        public ApiError(Date timestamp, int status, String error, String message, String path, List<Error> errors) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.errors = errors;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public List<Error> getErrors() {
            return errors;
        }
    }

    static class Error {
        private final String field;
        private final String message;
        private final String value;

        public Error(String field, String message, String value) {
            this.field = field;
            this.message = message;
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error> errorList = new LinkedList<>();

        for (ObjectError e : ex.getAllErrors()) {
            FieldError fieldError = (FieldError)e;
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            String value = (String)fieldError.getRejectedValue();

            Error error = new Error(field, message, value);
            errorList.add(error);
        }

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "Method Argument Not Valid Exception";
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiError apiError = new ApiError(new Date(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path, errorList);

        return ResponseEntity.badRequest().body(apiError);
    }
}