package com.devsuperior.DSCommerce.services;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.DSCommerce.dto.OrderDTO;
import com.devsuperior.DSCommerce.entities.Order;
import com.devsuperior.DSCommerce.entities.OrderItem;
import com.devsuperior.DSCommerce.entities.Product;
import com.devsuperior.DSCommerce.entities.User;
import com.devsuperior.DSCommerce.repositories.OrderItemRepository;
import com.devsuperior.DSCommerce.repositories.OrderRepository;
import com.devsuperior.DSCommerce.repositories.ProductRepository;
import com.devsuperior.DSCommerce.services.exceptions.ForbiddenException;
import com.devsuperior.DSCommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.DSCommerce.tests.OrderFactory;
import com.devsuperior.DSCommerce.tests.ProductFactory;
import com.devsuperior.DSCommerce.tests.UserFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;
	
	@Mock
	private OrderRepository repository;
	
	@Mock
	private AuthService authService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderItemRepository orderItemRepository;
	
	@Mock
	private UserService userService;
	
	private Long existingId, nonExistingId;
	private Long existingProductId, nonExistingProductId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception{
		
		existingId = 1L;
		nonExistingId = 2L;
		existingProductId = 1L;
		nonExistingProductId = 2L;
		
		admin = UserFactory.createCustomAdminUser(1L, "Ana", "ana@gmail.com");
		client = UserFactory.createCustomClientUser(2L, "Bob", "bob@gmail.com");
		
		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);
		
		product = ProductFactory.createProduct();
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(order));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(order);
		
		Mockito.when(orderItemRepository.saveAll(ArgumentMatchers.any())).thenReturn(new ArrayList<>(order.getItems()));
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() {
		
		Mockito.doNothing().when(authService).validateSelfOrAdmin(ArgumentMatchers.any());
		
		OrderDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		
		Mockito.verify(repository).findById(existingId);
		Mockito.verify(authService).validateSelfOrAdmin(ArgumentMatchers.any());
	}
	
	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogged() {
		
		Mockito.doNothing().when(authService).validateSelfOrAdmin(ArgumentMatchers.any());
		
		OrderDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);	
		
		Mockito.verify(repository).findById(existingId);
		Mockito.verify(authService).validateSelfOrAdmin(ArgumentMatchers.any());
	}
	
	@Test
	public void findByIdShouldThrowForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
		
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(ArgumentMatchers.any());
		
		Assertions.assertThrows(ForbiddenException.class, () -> {
			@SuppressWarnings("unused")
			OrderDTO result = service.findById(existingId);
		});
		
		Mockito.verify(repository).findById(existingId);
		Mockito.verify(authService).validateSelfOrAdmin(ArgumentMatchers.any());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(ArgumentMatchers.any());
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			OrderDTO result = service.findById(nonExistingId);
		});
		
		Mockito.verify(repository).findById(nonExistingId);
		Mockito.verify(authService, Mockito.never()).validateSelfOrAdmin(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void insertShouldReturnOrderDTOWhenUserLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(client);
		
		OrderDTO result = service.insert(orderDTO);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void insertShouldThrowUsernameNotFoundExceptionWhenUserNotLogged() {
		
		Mockito.doThrow(UsernameNotFoundException.class).when(userService).authenticated();
		
		order.setClient(new User());
		orderDTO = new OrderDTO(order);
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			OrderDTO result = service.insert(orderDTO);
		});
		
		Mockito.verify(productRepository, Mockito.never()).getReferenceById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void insertShouldThrowEntityNotFoundExceptionWhenOrderProductIdDoesNotExist() {
		
		Mockito.when(userService.authenticated()).thenReturn(admin);
		
		product.setId(nonExistingProductId);
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		
		orderDTO = new OrderDTO(order);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			OrderDTO result = service.insert(orderDTO);
		});
		
		Mockito.verify(productRepository).getReferenceById(nonExistingId);
	}
	
}
