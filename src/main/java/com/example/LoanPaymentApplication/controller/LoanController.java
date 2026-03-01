package com.example.LoanPaymentApplication.controller;

import com.example.LoanPaymentApplication.entity.Loan;
import com.example.LoanPaymentApplication.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createLoan(@RequestBody Map<String, Object> request) {
        BigDecimal loanAmount = new BigDecimal(request.get("loanAmount").toString());
        Integer term = Integer.parseInt(request.get("term").toString());

        Loan loan = loanService.createLoan(loanAmount, term);

        Map<String, Object> response = new HashMap<>();
        response.put("loanId", loan.getLoanId());
        response.put("loanAmount", loan.getLoanAmount());
        response.put("remainingBalance", loan.getRemainingBalance());
        response.put("term", loan.getTerm());
        response.put("status", loan.getStatus());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Map<String, Object>> getLoan(@PathVariable Long loanId) {
        Loan loan = loanService.getLoanById(loanId);

        Map<String, Object> response = new HashMap<>();
        response.put("loanId", loan.getLoanId());
        response.put("loanAmount", loan.getLoanAmount());
        response.put("remainingBalance", loan.getRemainingBalance());
        response.put("term", loan.getTerm());
        response.put("status", loan.getStatus());
        response.put("createdAt", loan.getCreatedAt());

        return ResponseEntity.ok(response);
    }
}
