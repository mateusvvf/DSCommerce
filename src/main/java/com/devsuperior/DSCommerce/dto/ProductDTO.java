package com.devsuperior.DSCommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.DSCommerce.entities.Category;
import com.devsuperior.DSCommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	
	@Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres.")
	@NotBlank(message = "O campo precisa ser preenchido.")
	private String name;
	
	@Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres.")
	@NotBlank(message = "O campo precisa ser preenchido.")
	private String description;
	
	@Positive(message = "O valor deve ser maior que 0.")
	private Double price;
	private String imgUrl;
	
	@NotEmpty(message = "O produto deve ter pelo menos uma categoria.")
	private List<CategoryDTO> categories = new ArrayList<>();
	
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		
		for (Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat));
		}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
}
