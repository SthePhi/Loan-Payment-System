package com.example.LoanPaymentApplication.service;

import com.example.LoanPaymentApplication.entity.Payment;

import java.math.BigDecimal;

public interface PaymentService {
    Payment recordPayment(Long loanId, BigDecimal paymentAmount);
}
