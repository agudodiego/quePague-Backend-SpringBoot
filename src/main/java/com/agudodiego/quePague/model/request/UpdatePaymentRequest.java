package com.agudodiego.quePague.model.request;

import com.agudodiego.quePague.model.entity.Payment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {

    @NotNull(message = "id can't be blank")
    private Integer paymentId;
    @NotBlank(message = "title can't be blank")
    private String title;
    private LocalDate payDate;
    private Boolean alreadyPaid;
    private String note;

    public static Payment toEntity(UpdatePaymentRequest request) {
        return new Payment().builder()
                .paymentId(request.getPaymentId())
                .title(request.getTitle())
                .payDate(request.getPayDate())
                .alreadyPaid(request.getAlreadyPaid())
                .note(request.getNote())
                .build();
    }
}
