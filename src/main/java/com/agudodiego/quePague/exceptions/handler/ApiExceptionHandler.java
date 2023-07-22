package com.agudodiego.quePague.exceptions.handler;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.ErrorResponse;
import com.agudodiego.quePague.exceptions.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    // 409 CONFLICT //
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorResponse handleconflict(DataIntegrityViolationException e) {
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.CONFLICT.value());
    }

    /* 500 */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ErrorProcessException.class)
    @ResponseBody
    public ErrorResponse handleErrorProcessException(ErrorProcessException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /* Elementos no encontrados */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.NOT_FOUND.value());
    }

    // Este metodo maneja los errores que se generan en las @Validations
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleArgumentNotValidException(MethodArgumentNotValidException notValidEx){
        List<String> errorsList = notValidEx.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResponse(errorsList, HttpStatus.BAD_REQUEST.value());
    }

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseBody
//    public ErrorResponse handleExpiredJwtException(BadCredentialsException e){
//        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.UNAUTHORIZED.value());
//    }

    /* Maneja cualquier otra excepcion no contemplada */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ErrorResponse handleNotFoundException(Exception e){
//        return new ErrorResponse(Arrays.asList("A server internal error occured"), HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }

}
