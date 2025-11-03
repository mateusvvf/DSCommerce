package com.devsuperior.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.DSCommerce.entities.OrderItem;
import com.devsuperior.DSCommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{
	
}
