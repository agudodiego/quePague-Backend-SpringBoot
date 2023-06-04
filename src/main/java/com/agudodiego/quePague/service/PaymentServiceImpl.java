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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService, PersonExistsService, PaymentExitsService {

    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;

    @Override
    public PaymentResponse saveOne(String username, Payment payment) throws ErrorProcessException {
        Person person = personRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("This person ("+username+") doesn´t exist in the DB"));
        for (Payment pa: person.getPayments()) {
            if (pa.getTitle().equals(payment.getTitle())) throw new DataIntegrityViolationException("Use another title for the payment");
        }
        try{
            person.getPayments().add(payment);
            personRepository.save(person);
            Payment justAddedPayment = personRepository.findByUsername(username).get().getPayments().stream()
                    .filter((p)-> p.getTitle().equals(payment.getTitle()))
                    .findFirst()
                    .orElseThrow(()->new NotFoundException("The payment doesn´t exist"));
            return PaymentResponse.toResponse(justAddedPayment);
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
