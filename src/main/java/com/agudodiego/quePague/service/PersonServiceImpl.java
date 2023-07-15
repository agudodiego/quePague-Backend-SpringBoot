package com.agudodiego.quePague.service;

import com.agudodiego.quePague.config.JwtService;
import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.entity.Role;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.AuthenticationResponse;
import com.agudodiego.quePague.model.response.BasicListPersonResponse;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PersonRepository;
import com.agudodiego.quePague.service.interfaces.PersonExistsService;
import com.agudodiego.quePague.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, PersonExistsService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterPersonRequest request) {
        if (personExists(request.getUsername())) throw new DataIntegrityViolationException("This user already exist.");
        var person = Person.builder()
                .username(request.getUsername())
                .pass(passwordEncoder.encode(request.getPass()))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        personRepository.save(person);
        return "Person added to the database";
    }

    @Override
    public AuthenticationResponse login(LoginPersonRequest request) throws ErrorProcessException {
        // si el siguiente metodo falla tira una excepcion
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPass()));
        // caso contrario necesito generar un token para ese usuario y devolverlo
        var person = personRepository.findByUsername(request.getUsername()).orElseThrow(()-> new ErrorProcessException("An error occurred in the process"));
        // las dos lineas siguientes son para agregar en el payload del token datos exta (en este caso el ROL de la persona)
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", person.getRole().name());
        var jwtToken = jwtService.generateToken(extraClaims, person);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
    public List<BasicListPersonResponse> getBasicList() throws ErrorProcessException {
        try{
            List<Person> people = personRepository.findAll();
            return people.stream()
                    .map(BasicListPersonResponse::toResponse)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public String deletePerson(String username) throws ErrorProcessException {
        Person personToDelete = personRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("This person ("+username+") doesn´t exist in the DB"));
        try{
            personRepository.delete(personToDelete);
            return username + "was deleted";
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }


    @Override
    public Boolean personExists(String username) {
        return personRepository.existsByUsername(username);
    }
}
