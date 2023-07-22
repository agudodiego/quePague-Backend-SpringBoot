package com.agudodiego.quePague.config.securityExceptionHandler;

import com.agudodiego.quePague.exceptions.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.Arrays;

@ControllerAdvice
public class DefaultExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ErrorResponse handleconflict(SignatureException e) {
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.UNAUTHORIZED.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorResponse handleExpiredJwtException(AccessDeniedException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseBody
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(Exception e) {
        return new ErrorResponse(Arrays.asList("este agarra todas las exc"), HttpStatus.UNAUTHORIZED.value());
    }

}
