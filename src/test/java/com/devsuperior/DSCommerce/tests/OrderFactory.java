package com.devsuperior.DSCommerce.tests;

import java.time.Instant;

import com.devsuperior.DSCommerce.entities.Order;
import com.devsuperior.DSCommerce.entities.OrderItem;
import com.devsuperior.DSCommerce.entities.OrderStatus;
import com.devsuperior.DSCommerce.entities.Payment;
import com.devsuperior.DSCommerce.entities.Product;
import com.devsuperior.DSCommerce.entities.User;

public class OrderFactory {

	public static Order createOrder(User client) {
		Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client, new Payment());
		Product product = ProductFactory.createProduct();
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		
		return order;
	}
	
}
