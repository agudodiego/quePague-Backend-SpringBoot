package com.agudodiego.quePague.model.response;

import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private Integer paymentId;
    private String title;
    private LocalDate payDate;
    private Boolean alreadyPaid;
    private String note;

    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .title(payment.getTitle())
                .payDate(payment.getPayDate())
                .alreadyPaid(payment.getAlreadyPaid())
                .note(payment.getNote())
                .build();
    }
}
