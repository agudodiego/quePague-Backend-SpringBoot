package com.agudodiego.quePague.service;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PersonRepository;
import com.agudodiego.quePague.service.interfaces.PersonExistService;
import com.agudodiego.quePague.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, PersonExistService {

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
        if (!person.getEmail().equals(request.getPass())) throw new NotFoundException("Wrong username or password");
        try{
            return PersonResponse.toResponse(person);
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public Boolean personExists(String username) {
        return personRepository.existsByUsername(username);
    }
}
