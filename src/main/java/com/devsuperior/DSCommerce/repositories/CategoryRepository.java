package com.devsuperior.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.DSCommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
