package com.example.LoanPaymentApplication.controller;

import com.example.LoanPaymentApplication.entity.Payment;
import com.example.LoanPaymentApplication.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> recordPayment(@RequestBody Map<String, Object> request) {
        Long loanId = Long.parseLong(request.get("loanId").toString());
        BigDecimal paymentAmount = new BigDecimal(request.get("paymentAmount").toString());

        Payment payment = paymentService.recordPayment(loanId, paymentAmount);

        Map<String, Object> response = new HashMap<>();
        response.put("paymentId", payment.getPaymentId());
        response.put("loanId", payment.getLoanId());
        response.put("paymentAmount", payment.getPaymentAmount());
        response.put("paymentDate", payment.getPaymentDate());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
