package com.devsuperior.DSCommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.DSCommerce.dto.CategoryDTO;
import com.devsuperior.DSCommerce.entities.Category;
import com.devsuperior.DSCommerce.repositories.CategoryRepository;
import com.devsuperior.DSCommerce.tests.CategoryFactory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	private Category category;
	private List<Category> categories;
	
	@BeforeEach
	void setUp() throws Exception {
		
		category = CategoryFactory.createCategory();
		categories = new ArrayList<>();
		
		categories.add(category);
		
		Mockito.when(repository.findAll()).thenReturn(categories);		
	}
	
	@Test
	public void findAllShouldReturnListOfCategoryDTO() {
		List<CategoryDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.get(0).getId(), category.getId());
		Assertions.assertEquals(result.get(0).getName(), category.getName());
		
		Mockito.verify(repository).findAll();
	}	
	
}
