package com.example.LoanPaymentApplication.payment;

import com.example.LoanPaymentApplication.entity.Loan;
import com.example.LoanPaymentApplication.entity.Payment;
import com.example.LoanPaymentApplication.repository.PaymentRepository;
import com.example.LoanPaymentApplication.service.LoanService;
import com.example.LoanPaymentApplication.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Loan testLoan;
    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testLoan = new Loan(new BigDecimal("1000.00"), 12);
        testLoan.setLoanId(1L);

        testPayment = new Payment(1L, new BigDecimal("500.00"));
        testPayment.setPaymentId(1L);
    }

    @Test
    void testRecordPayment_Success() {
        when(loanService.updateLoanBalance(1L, new BigDecimal("500.00"))).thenReturn(testLoan);
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        Payment recordedPayment = paymentService.recordPayment(1L, new BigDecimal("500.00"));

        assertNotNull(recordedPayment);
        assertEquals(1L, recordedPayment.getPaymentId());
        assertEquals(1L, recordedPayment.getLoanId());
        assertEquals(new BigDecimal("500.00"), recordedPayment.getPaymentAmount());

        verify(loanService, times(1)).updateLoanBalance(1L, new BigDecimal("500.00"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testRecordPayment_InvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.recordPayment(1L, new BigDecimal("-100.00"));
        });
    }
}
