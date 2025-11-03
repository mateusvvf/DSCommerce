package com.devsuperior.DSCommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCommerce.dto.OrderDTO;
import com.devsuperior.DSCommerce.entities.Order;
import com.devsuperior.DSCommerce.repositories.OrderRepository;
import com.devsuperior.DSCommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Optional<Order> result = repository.findById(id);
		Order order = result.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		OrderDTO dto = new OrderDTO(order);
		return dto;
	}

}
