package com.agudodiego.quePague.service.interfaces;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.request.UpdatePaymentRequest;
import com.agudodiego.quePague.model.response.PaymentResponse;
import com.agudodiego.quePague.model.response.PersonResponse;

public interface PaymentService {

    PaymentResponse saveOne(String username, Payment payment) throws ErrorProcessException;
    PaymentResponse updatePayment(Payment payment) throws ErrorProcessException;
    String deletePayment(Integer id) throws ErrorProcessException;
}
