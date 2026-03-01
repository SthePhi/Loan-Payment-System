package com.example.LoanPaymentApplication.loan;

import com.example.LoanPaymentApplication.entity.Loan;

import com.example.LoanPaymentApplication.entity.LoanStatus;
import com.example.LoanPaymentApplication.exception.LoanNotFoundException;
import com.example.LoanPaymentApplication.exception.OverpaymentException;
import com.example.LoanPaymentApplication.service.LoanServiceImpl;
import com.example.LoanPaymentApplication.repository.LoanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    private Loan testLoan;

    @BeforeEach
    void setUp() {
        testLoan = new Loan(new BigDecimal("1000.00"), 12);
        testLoan.setLoanId(1L);
    }

    @Test
    void testCreateLoan_Success() {
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan createdLoan = loanService.createLoan(new BigDecimal("1000.00"), 12);

        assertNotNull(createdLoan);
        assertEquals(new BigDecimal("1000.00"), createdLoan.getLoanAmount());
        assertEquals(new BigDecimal("1000.00"), createdLoan.getRemainingBalance());
        assertEquals(12, createdLoan.getTerm());
        assertEquals(LoanStatus.ACTIVE, createdLoan.getStatus());

        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testCreateLoan_InvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(new BigDecimal("-100.00"), 12);
        });
    }

    @Test
    void testCreateLoan_InvalidTerm() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(new BigDecimal("1000.00"), 0);
        });
    }

    @Test
    void testGetLoanById_Success() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(testLoan));

        Loan foundLoan = loanService.getLoanById(1L);

        assertNotNull(foundLoan);
        assertEquals(1L, foundLoan.getLoanId());
        assertEquals(new BigDecimal("1000.00"), foundLoan.getLoanAmount());
    }

    @Test
    void testGetLoanById_NotFound() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> {
            loanService.getLoanById(99L);
        });
    }

    @Test
    void testUpdateLoanBalance_PartialPayment() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(testLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan updatedLoan = loanService.updateLoanBalance(1L, new BigDecimal("300.00"));

        assertEquals(new BigDecimal("700.00"), updatedLoan.getRemainingBalance());
        assertEquals(LoanStatus.ACTIVE, updatedLoan.getStatus());
    }

    @Test
    void testUpdateLoanBalance_FullPayment() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(testLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan updatedLoan = loanService.updateLoanBalance(1L, new BigDecimal("1000.00"));

        assertEquals(0, new BigDecimal("0.00").compareTo(updatedLoan.getRemainingBalance()));
        assertEquals(LoanStatus.SETTLED, updatedLoan.getStatus());
    }

    @Test
    void testUpdateLoanBalance_Overpayment() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(testLoan));

        assertThrows(OverpaymentException.class, () -> {
            loanService.updateLoanBalance(1L, new BigDecimal("1500.00"));
        });
    }
}
