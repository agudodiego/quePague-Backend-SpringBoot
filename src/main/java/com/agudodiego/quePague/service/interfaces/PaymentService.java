package com.agudodiego.quePague.service.interfaces;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.response.PersonResponse;

public interface PaymentService {

    PersonResponse saveOne(String username, Payment payment) throws ErrorProcessException;
}
