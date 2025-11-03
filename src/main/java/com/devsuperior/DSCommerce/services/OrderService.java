package com.devsuperior.DSCommerce.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCommerce.dto.OrderDTO;
import com.devsuperior.DSCommerce.dto.OrderItemDTO;
import com.devsuperior.DSCommerce.entities.Order;
import com.devsuperior.DSCommerce.entities.OrderItem;
import com.devsuperior.DSCommerce.entities.OrderStatus;
import com.devsuperior.DSCommerce.entities.Product;
import com.devsuperior.DSCommerce.entities.User;
import com.devsuperior.DSCommerce.repositories.OrderItemRepository;
import com.devsuperior.DSCommerce.repositories.OrderRepository;
import com.devsuperior.DSCommerce.repositories.ProductRepository;
import com.devsuperior.DSCommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Optional<Order> result = repository.findById(id);
		Order order = result.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		
		authService.validateSelfOrAdmin(order.getClient().getId());
		
		OrderDTO dto = new OrderDTO(order);
		return dto;
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		order = repository.save(order);
		
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
		
	}

}
