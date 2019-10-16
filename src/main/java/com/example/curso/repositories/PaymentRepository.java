package com.example.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.curso.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
