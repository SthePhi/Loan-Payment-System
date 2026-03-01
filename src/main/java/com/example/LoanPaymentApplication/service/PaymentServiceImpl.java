package com.example.LoanPaymentApplication.service;

import com.example.LoanPaymentApplication.entity.Loan;
import com.example.LoanPaymentApplication.entity.Payment;
import com.example.LoanPaymentApplication.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanService loanService;

    @Override
    @Transactional
    public Payment recordPayment(Long loanId, BigDecimal paymentAmount) {
        if (paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        // Update loan balance first
        Loan updatedLoan = loanService.updateLoanBalance(loanId, paymentAmount);

        // Record the payment
        Payment payment = new Payment(loanId, paymentAmount);
        return paymentRepository.save(payment);
    }
}
