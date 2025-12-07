package dev.nelon.dreamshops.request;

import lombok.Data;
import dev.nelon.dreamshops.model.Category;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
}
