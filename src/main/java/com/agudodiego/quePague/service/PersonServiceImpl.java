package com.agudodiego.quePague.service;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PersonRepository;
import com.agudodiego.quePague.service.interfaces.PersonExistsService;
import com.agudodiego.quePague.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, PersonExistsService {

    private final PersonRepository personRepository;

    @Override
    public String saveOne(RegisterPersonRequest request) {
        if (personExists(request.getUsername())) throw new DataIntegrityViolationException("This user already exist.");
        personRepository.save(RegisterPersonRequest.toEntity(request));
        return "Person saved in DB";
//        return PersonResponse.toResponse(personRepository.save(RegisterPersonRequest.toEntity(request)));
    }

    @Override
    public PersonResponse getOne(LoginPersonRequest request) throws ErrorProcessException {
        Person person = personRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Wrong username or password"));
        if (!person.getPass().equals(request.getPass())) throw new NotFoundException("Username or password doesn´t match");
        try{
            return PersonResponse.toResponse(person);
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public PersonResponse getOneByUsername(String username) throws ErrorProcessException {
        if (!personRepository.existsByUsername(username)) throw new NotFoundException("This person ("+username+") doesn´t exist in the DB");
        try{
            return PersonResponse.toResponse(personRepository.findByUsername(username).get());
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public Boolean personExists(String username) {
        return personRepository.existsByUsername(username);
    }
}
