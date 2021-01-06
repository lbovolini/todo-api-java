package com.gitlab.lbovolini.todo.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    static class ApiError {
        private final ZonedDateTime timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final String path;
        private final List<Error> errors;

        public ApiError(ZonedDateTime timestamp, int status, String error, String message, String path, List<Error> errors) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.errors = errors;
        }

        public ZonedDateTime getTimestamp() {
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

        ApiError apiError = new ApiError(ZonedDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path, errorList);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException ex, HttpServletRequest request) {

        List<Error> errorList = List.of(getError(ex));
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        String message = "Duplicate Key Exception";
        String path = request.getRequestURI();

        ApiError apiError = new ApiError(ZonedDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path, errorList);

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    // !todo testar
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();

        ApiError apiError = new ApiError(ZonedDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage(), path, List.of());

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    /**
     * Handle restAuthenticationFailureHandler
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     */
    public void handleRestAuthentication(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String path = request.getRequestURI();

        ApiError apiError = new ApiError(ZonedDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage(), path, List.of());

        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), apiError);
    }

    private Error getError(DuplicateKeyException ex) {

        String exceptionMessage = Objects.requireNonNullElse(ex.getMostSpecificCause().getMessage(), "");

        int start = exceptionMessage.indexOf("{");
        int end = exceptionMessage.indexOf("}");

        String messageError = ((start != -1 && end != -1) &&  (start + 1) < exceptionMessage.length())
                ? exceptionMessage.substring(start + 1, end)
                : exceptionMessage;

        String[] fieldValue = messageError.replaceFirst("\"", "")
                .replaceFirst("\"", "")
                .replaceAll(" ", "")
                .split(":");

        String field = fieldValue.length > 0 ? fieldValue[0] : "";
        String value = fieldValue.length > 1 ? fieldValue[1] : "";
        String message = "Duplicate Key";

        return new Error(field, message, value);
    }
}
