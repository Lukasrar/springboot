package com.example.curso.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.curso.entities.Order;
import com.example.curso.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order>findByClient(User client);
}
