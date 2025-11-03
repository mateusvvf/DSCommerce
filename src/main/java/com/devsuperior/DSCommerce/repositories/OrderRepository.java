package com.devsuperior.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.DSCommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
