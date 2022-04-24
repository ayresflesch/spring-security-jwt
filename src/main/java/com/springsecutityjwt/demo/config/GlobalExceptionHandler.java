package com.springsecutityjwt.demo.config;

import com.springsecutityjwt.demo.dto.response.ErrorResponse;
import com.springsecutityjwt.demo.exception.ResourceNotFoundException;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    public ErrorResponse handleUsernameNotFoundException(UsernameAlreadyUsedException exception) {
        return new ErrorResponse("username", exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleUsernameNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponse(null, exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return fieldErrors
            .stream()
            .map(e -> new ErrorResponse(e.getField(), e.getDefaultMessage())).collect(Collectors.toList());
    }
}
