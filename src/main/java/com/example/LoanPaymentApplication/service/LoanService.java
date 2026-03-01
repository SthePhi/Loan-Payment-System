package com.example.LoanPaymentApplication.service;

import com.example.LoanPaymentApplication.entity.Loan;

import java.math.BigDecimal;

public interface LoanService {
    Loan createLoan(BigDecimal loanAmount, Integer term);
    Loan getLoanById(Long loanId);
    Loan updateLoanBalance(Long loanId, BigDecimal paymentAmount);
}
