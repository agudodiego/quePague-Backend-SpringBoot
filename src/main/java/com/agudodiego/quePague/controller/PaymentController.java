package com.agudodiego.quePague.controller;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.request.AddPaymentRequest;
import com.agudodiego.quePague.model.request.UpdatePaymentRequest;
import com.agudodiego.quePague.model.response.GenericResponse;
import com.agudodiego.quePague.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping()
    public ResponseEntity<GenericResponse> saveOnePayment(@Valid @RequestBody AddPaymentRequest request) throws ErrorProcessException{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(Boolean.TRUE, "Payment created", paymentService.saveOne(request.getUsername(), AddPaymentRequest.toEntity(request))));
    }

    @PutMapping()
    public ResponseEntity<GenericResponse> updateOnePayment(@Valid @RequestBody UpdatePaymentRequest request) throws ErrorProcessException{
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new GenericResponse(Boolean.TRUE, "payment updated", paymentService.updatePayment(UpdatePaymentRequest.toEntity(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteOnePayment(@PathVariable Integer id) throws ErrorProcessException{
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse(Boolean.TRUE, "" , paymentService.deletePayment(id)));
    }
}
