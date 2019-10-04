package com.example.curso.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.example.curso.entities.Product;

public class ProductInsertDTO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty(message = "Cant be empty" )
	@Length(min = 3, max = 80, message = "lenght must be between 3 and 80")
	private String name;
	
	@NotEmpty(message = "Cant be empty" )
	@Length(min = 8, message = "min lenght must be 8")
	private String description;
	
	@Positive(message = "Cant be negative")
	private Double price;
	private String imgUrl;
	
	
	public ProductInsertDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductInsertDTO (Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
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
	
	public Product toEntity () {
		return new Product(id, name, description, price, imgUrl);
	}
	
}
