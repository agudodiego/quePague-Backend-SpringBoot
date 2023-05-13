package com.agudodiego.quePague.controller;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.request.PaymentRequest;
import com.agudodiego.quePague.model.response.GenericResponse;
import com.agudodiego.quePague.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping()
    public ResponseEntity<GenericResponse> saveOnePayment(@Valid @RequestBody PaymentRequest request) throws ErrorProcessException{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(Boolean.TRUE, "Payment created", paymentService.saveOne(request.getUsername(), PaymentRequest.toEntity(request))));
    }
}
