package com.example.LoanPaymentApplication.service;

import com.example.LoanPaymentApplication.entity.Loan;
import com.example.LoanPaymentApplication.entity.LoanStatus;
import com.example.LoanPaymentApplication.exception.LoanNotFoundException;
import com.example.LoanPaymentApplication.exception.OverpaymentException;
import com.example.LoanPaymentApplication.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanRepository;

    @Override
    @Transactional
    public Loan createLoan(BigDecimal loanAmount, Integer term) {
        if (loanAmount == null || loanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }
        if (term == null || term <= 0) {
            throw new IllegalArgumentException("Term must be positive");
        }

        Loan loan = new Loan(loanAmount, term);
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));
    }

    @Override
    @Transactional
    public Loan updateLoanBalance(Long loanId, BigDecimal paymentAmount) {
        Loan loan = getLoanById(loanId);

        if (loan.getStatus() == LoanStatus.SETTLED) {
            throw new IllegalStateException("Loan is already settled");
        }

        BigDecimal newBalance = loan.getRemainingBalance().subtract(paymentAmount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new OverpaymentException("Payment amount exceeds remaining balance");
        }

        loan.setRemainingBalance(newBalance);

        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus(LoanStatus.SETTLED);
        }

        loan.setUpdatedAt(LocalDateTime.now());
        return loanRepository.save(loan);
    }
}
