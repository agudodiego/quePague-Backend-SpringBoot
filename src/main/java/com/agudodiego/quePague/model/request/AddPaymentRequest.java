package com.agudodiego.quePague.model.request;

import com.agudodiego.quePague.model.entity.Payment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPaymentRequest {

    @NotBlank(message = "username can't be blank")
    @Size(min = 6, message = "username must have more than 5 characters")
    private String username;
    @NotBlank(message = "title can't be blank")
    private String title;

    public static Payment toEntity(AddPaymentRequest request) {
        Payment payment = new Payment();
        payment.setTitle(request.getTitle());
        payment.setAlreadyPaid(false);
        payment.setIsPayMonth(0);
        return payment;
    }
}
