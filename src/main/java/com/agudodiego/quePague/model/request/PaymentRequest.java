package com.agudodiego.quePague.model.request;

import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    @NotBlank(message = "username can't be blank")
    @Size(min = 6, message = "username must have more than 5 characters")
    private String username;
    @NotBlank(message = "title can't be blank")
    private String title;

    public static Payment toEntity(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setTitle(request.getTitle());
        payment.setAlreadyPaid(false);
        return payment;
    }
}
