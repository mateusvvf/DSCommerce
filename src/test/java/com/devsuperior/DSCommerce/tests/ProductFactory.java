package com.devsuperior.DSCommerce.tests;

import com.devsuperior.DSCommerce.entities.Category;
import com.devsuperior.DSCommerce.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Category category = CategoryFactory.createCategory();
		Product product = new Product(1L, "PC Gamer", "PC Gamer description.", 1200.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg");
		product.getCategories().add(category);
		return product;
	}
	
	public static Product createProduct(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}

}
