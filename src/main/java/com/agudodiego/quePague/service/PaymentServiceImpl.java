package com.agudodiego.quePague.service;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PaymentRepository;
import com.agudodiego.quePague.repository.PersonRepository;
import com.agudodiego.quePague.service.interfaces.PaymentService;
import com.agudodiego.quePague.service.interfaces.PersonExistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService, PersonExistService {

    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;

    @Override
    public PersonResponse saveOne(String username, Payment payment) throws ErrorProcessException {
        if (!personExists(username)) throw new NotFoundException("This person ("+username+") doesn´t exist in the DB");
        try{
            Person person = personRepository.findByUsername(username).get();
            person.getPayments().add(payment);
            return PersonResponse.toResponse(personRepository.save(person));
        } catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public Boolean personExists(String username) {
        return personRepository.existsByUsername(username);
    }
}
