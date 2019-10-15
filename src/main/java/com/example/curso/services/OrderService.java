package com.example.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.dto.OrderDTO;
import com.example.curso.dto.OrderItemDTO;
import com.example.curso.entities.Order;
import com.example.curso.entities.OrderItem;
import com.example.curso.entities.User;
import com.example.curso.exceptions.ResourceNotFoundException;
import com.example.curso.repositories.OrderRepository;
import com.example.curso.repositories.UserRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity =  obj.orElseThrow(() -> new ResourceNotFoundException(id));	
		authService.validateOnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}
	
	public List<OrderDTO> findByClient() {
		User client = authService.authenticated();
		List<Order> list = repository.findByClient(client);
		return list.stream().map( e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderItemDTO> findItems(Long id) {
	Order order = repository.getOne(id);
	authService.validateOnOrderOrAdmin(order);
	Set<OrderItem> set = order.getItems();
	return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderDTO> findByClientId(Long clientId) {
		User client = userRepository.getOne(clientId);
		List<Order> list = repository.findByClient(client);
		return list.stream().map( e -> new OrderDTO(e)).collect(Collectors.toList());
	}
}
