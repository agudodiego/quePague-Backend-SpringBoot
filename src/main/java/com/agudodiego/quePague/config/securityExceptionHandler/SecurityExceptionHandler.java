package com.agudodiego.quePague.config.securityExceptionHandler;

import com.agudodiego.quePague.exceptions.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class SecurityExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ErrorResponse handleSignatureException(SignatureException e) {
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.UNAUTHORIZED.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException e){
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.FORBIDDEN.value());
    }

    // este metodo agarra todas las excepciones
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(Exception e) {
        return new ErrorResponse(Arrays.asList(e.getMessage()), HttpStatus.UNAUTHORIZED.value());
    }

}
