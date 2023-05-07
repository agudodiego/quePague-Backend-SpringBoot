package com.agudodiego.quePague.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

    public boolean success = false;
    public final List<String> message;
    public final Integer statusCode;
    public LocalDate timestamp = LocalDate.now();

}
