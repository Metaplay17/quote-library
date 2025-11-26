package org.example.controllers;

import org.example.dto.DefaultResponse;
import org.example.exceptions.AccessDeniedException;
import org.example.exceptions.IllegalRequestArgumentsException;
import org.example.exceptions.IllegalRequestException;
import org.example.exceptions.InvalidUsernamePasswordCombinationException;
import org.example.exceptions.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    public ResponseEntity<DefaultResponse> handleEmailAlreadyUsedException(UsernameAlreadyUsedException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.BAD_REQUEST, "Username уже используется");
        log.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidUsernamePasswordCombinationException.class)
    public ResponseEntity<DefaultResponse> handleInvalidPasswordException(InvalidUsernamePasswordCombinationException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.FORBIDDEN, "Не найдена указанная комбинация email + password");
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder messages = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            messages.append(error.getDefaultMessage())
                    .append("; ")
        );

        String message = messages.length() > 0 
            ? messages.substring(0, messages.length() - 2) // убрать последнюю "; "
            : "Validation failed";

        DefaultResponse response = new DefaultResponse(HttpStatus.BAD_REQUEST, message);
        log.warn(message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Неверный формат JSON, попробуйте проверить типы данных";
        
        log.warn("JSON parse error", ex.getMessage());
        
        return ResponseEntity.badRequest()
                .body(new DefaultResponse(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalRequestArgumentsException.class)
    public ResponseEntity<DefaultResponse> handleIllegalRequestArgumentsException(IllegalRequestArgumentsException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.CONFLICT, ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<DefaultResponse> handleIllegalRequestException(IllegalRequestException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.CONFLICT, ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<DefaultResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.METHOD_NOT_ALLOWED, "Недопустимый тип запроса");
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotFoundException(NoResourceFoundException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.NOT_FOUND, "Запрашиваемый ресурс не найден");
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DefaultResponse> handleAccessDeniedException(AccessDeniedException ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        log.warn(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }





    @ExceptionHandler(Throwable.class)
    public ResponseEntity<DefaultResponse> handleException(Throwable ex) {
        DefaultResponse response = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
        log.error(ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
