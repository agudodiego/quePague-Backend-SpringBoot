package com.agudodiego.quePague.controller;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.GenericResponse;
import com.agudodiego.quePague.service.interfaces.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerPerson(@Valid @RequestBody RegisterPersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(Boolean.TRUE, "Person created", personService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> loginPerson(@Valid @RequestBody LoginPersonRequest request) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(Boolean.TRUE, "Person logged", personService.getOne(request)));
    }
}
