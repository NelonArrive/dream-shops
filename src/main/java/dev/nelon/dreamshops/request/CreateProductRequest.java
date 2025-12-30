package dev.nelon.dreamshops.request;

import dev.nelon.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
}
