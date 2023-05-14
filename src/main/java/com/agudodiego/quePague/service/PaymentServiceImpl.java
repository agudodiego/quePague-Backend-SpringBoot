package com.agudodiego.quePague.service;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.request.UpdatePaymentRequest;
import com.agudodiego.quePague.model.response.PaymentResponse;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PaymentRepository;
import com.agudodiego.quePague.repository.PersonRepository;
import com.agudodiego.quePague.service.interfaces.PaymentExitsService;
import com.agudodiego.quePague.service.interfaces.PaymentService;
import com.agudodiego.quePague.service.interfaces.PersonExistsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService, PersonExistsService, PaymentExitsService {

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
    public PaymentResponse updatePayment(Payment payment) throws ErrorProcessException {
        if (!paymentExits(payment.getPaymentId())) throw new NotFoundException("The payment doesn´t exist");
        try{
            return PaymentResponse.toResponse(paymentRepository.save(payment));
        }catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public String deletePayment(Integer id) throws ErrorProcessException {
        if (!paymentExits(id)) throw new NotFoundException("The payment doesn´t exist");
        try{
            paymentRepository.delete(paymentRepository.findById(id).get());
            return "Payment deleted!";
        }catch (RuntimeException e) {
            throw new ErrorProcessException("An error occurred in the process: " + e.getMessage());
        }
    }

    @Override
    public Boolean personExists(String username) {
        return personRepository.existsByUsername(username);
    }

    @Override
    public Boolean paymentExits(Integer id) {
        return paymentRepository.existsById(id);
    }
}
