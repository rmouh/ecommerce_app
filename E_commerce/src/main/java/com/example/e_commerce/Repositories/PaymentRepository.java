package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserEmail(String userEmail);
}