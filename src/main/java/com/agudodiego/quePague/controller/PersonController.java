package com.agudodiego.quePague.controller;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.response.GenericResponse;
import com.agudodiego.quePague.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("{username}")
    public ResponseEntity<GenericResponse> getOnePerson(@PathVariable String username) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(Boolean.TRUE, "Person found", personService.getOneByUsername(username)));
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllBasicPeople() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(Boolean.TRUE, "List of people found", personService.getBasicList()));
    }

    @DeleteMapping("{username}")
    public ResponseEntity<GenericResponse> deletePerson(@PathVariable String username) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(Boolean.TRUE, "Deletion OK", personService.deletePerson(username)));
    }
}
