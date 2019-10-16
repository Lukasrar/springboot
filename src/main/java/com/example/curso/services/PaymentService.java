package com.example.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.dto.CategoryDTO;
import com.example.curso.dto.PaymentDTO;
import com.example.curso.entities.Category;
import com.example.curso.entities.Order;
import com.example.curso.entities.Payment;
import com.example.curso.exceptions.ResourceNotFoundException;
import com.example.curso.repositories.OrderRepository;
import com.example.curso.repositories.PaymentRepository;
import com.example.curso.repositories.ProductRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired ProductRepository productRepository;
	public List<PaymentDTO> findAll() {
		List<Payment> list = repository.findAll();
		return list.stream().map( e -> new PaymentDTO(e)).collect(Collectors.toList());
	}
	
	public PaymentDTO findById(Long id) {
		Optional<Payment> obj = repository.findById(id);
		
		Payment entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new PaymentDTO(entity);	
	}
	
	@Transactional
	public PaymentDTO insert(PaymentDTO dto) {
		Order order = orderRepository.getOne(dto.getOrderId());
		Payment payment = new Payment(null, dto.getMoment(), order);
		order.setPayment(payment);
		orderRepository.save(order);
		return new PaymentDTO(order.getPayment());
	}
	
	@Transactional
	public PaymentDTO update(Long id, PaymentDTO dto) {
		try {
			Payment entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new PaymentDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}
	
	private void updateData(Payment entity, PaymentDTO dto) {
		entity.setMoment(dto.getMoment());
	}
}
