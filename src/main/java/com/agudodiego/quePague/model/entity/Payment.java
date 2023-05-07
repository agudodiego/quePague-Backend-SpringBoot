package com.agudodiego.quePague.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer payment_id;
    @Column(name = "title", length = 45, nullable = false)
    private String title;
    @Column(name = "pay_date")
    private LocalDate payDate;
    @Column(name = "already_paid", nullable = false)
    private Boolean alreadyPaid;
}
