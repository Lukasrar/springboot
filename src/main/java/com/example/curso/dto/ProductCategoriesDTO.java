package com.example.curso.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.example.curso.entities.Product;

public class ProductCategoriesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Cant be empty" )
	@Length(min = 3, max = 80, message = "lenght must be between 3 and 80")
	private String name;
	
	@NotEmpty(message = "Cant be empty" )
	@Length(min = 8, message = "min lenght must be 8")
	private String description;
	
	@PositiveOrZero
	private Double price;
	
	private String imgUrl;
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductCategoriesDTO() {
		
	}

	public ProductCategoriesDTO(String name, String description, Double price, String imgUrl,
			List<CategoryDTO> categories) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.categories = categories;
	}
	
	public ProductCategoriesDTO(Product entity) {
		setName(entity.getName());
		setDescription(entity.getDescription());
		setPrice(entity.getPrice());
		setImgUrl(entity.getImgUrl());
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

	public Product toEntity() {
		return new Product(null, name, description, price, imgUrl);
	}
	
	
	
}
