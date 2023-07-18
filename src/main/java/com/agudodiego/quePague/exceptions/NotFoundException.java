package com.agudodiego.quePague.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String detail) {
        super(detail);
    }
}
