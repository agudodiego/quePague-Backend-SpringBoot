package com.agudodiego.quePague.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@Builder // lo necesito en la entidad para poder mapear luego en el dto
@AllArgsConstructor // @Builder necesita de esta annotation
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "title", length = 45, nullable = false)
    private String title;
    @Column(name = "pay_date")
    private LocalDate payDate;
    @Column(name = "already_paid", nullable = false)
    private Boolean alreadyPaid;
    @Column(name = "is_pay_month")
    private Integer isPayMonth;
    @Column(name = "note", length = 255)
    private String note;
}
