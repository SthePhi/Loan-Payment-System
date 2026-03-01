package com.example.LoanPaymentApplication.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long loanId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // Constructors
    public Payment() {
        this.paymentDate = LocalDateTime.now();
    }

    public Payment(Long loanId, BigDecimal paymentAmount) {
        this.loanId = loanId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = LocalDateTime.now();
    }
}
