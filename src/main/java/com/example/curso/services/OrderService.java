package com.example.curso.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.dto.OrderDTO;
import com.example.curso.dto.OrderItemDTO;
import com.example.curso.entities.Order;
import com.example.curso.entities.OrderItem;
import com.example.curso.entities.Product;
import com.example.curso.entities.User;
import com.example.curso.entities.enums.OrderStatus;
import com.example.curso.exceptions.ResourceNotFoundException;
import com.example.curso.repositories.OrderItemRepository;
import com.example.curso.repositories.OrderRepository;
import com.example.curso.repositories.ProductRepository;
import com.example.curso.repositories.UserRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
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

	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
		User client = authService.authenticated();
		Order order = new Order(null, Instant.now(), OrderStatus.WAITING_PAIMENT, client);
		
		for(OrderItemDTO itemDto: dto) {
			Product product = productRepository.getOne(itemDto.getProductId());
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), itemDto.getPrice());
			order.getItems().add(item);
		}
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO update(Long id, OrderDTO dto) {
		try {
			Order entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new OrderDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	private void updateData(Order entity, OrderDTO dto) {
		entity.setStatus(dto.getStatus());
	}
}
