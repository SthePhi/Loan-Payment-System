package com.example.LoanPaymentApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal loanAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal remainingBalance;

    @Column(nullable = false)
    private Integer term;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Loan() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = LoanStatus.ACTIVE;
    }

    public Loan(BigDecimal loanAmount, Integer term) {
        this();
        this.loanAmount = loanAmount;
        this.remainingBalance = loanAmount;
        this.term = term;
    }
}
