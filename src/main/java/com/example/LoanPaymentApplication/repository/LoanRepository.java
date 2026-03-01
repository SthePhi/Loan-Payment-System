package com.example.LoanPaymentApplication.repository;

import com.example.LoanPaymentApplication.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
