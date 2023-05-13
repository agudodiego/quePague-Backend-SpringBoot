package com.agudodiego.quePague.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    public Boolean success;
    public String message;
    public Object data;

}
