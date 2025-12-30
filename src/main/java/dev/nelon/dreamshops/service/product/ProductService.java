package dev.nelon.dreamshops.service.product;

import dev.nelon.dreamshops.dto.ImageDto;
import dev.nelon.dreamshops.dto.ProductDto;
import dev.nelon.dreamshops.exception.AlreadyExistException;
import dev.nelon.dreamshops.exception.ProductNotFoundException;
import dev.nelon.dreamshops.model.Category;
import dev.nelon.dreamshops.model.Image;
import dev.nelon.dreamshops.model.Product;
import dev.nelon.dreamshops.repository.CategoryRepository;
import dev.nelon.dreamshops.repository.ImageRepository;
import dev.nelon.dreamshops.repository.ProductRepository;
import dev.nelon.dreamshops.request.CreateProductRequest;
import dev.nelon.dreamshops.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;
	
	@Override
	public Product addProduct(CreateProductRequest request) {
		
		if (productExists(request.getName(), request.getBrand())) {
			throw new AlreadyExistException(request.getBrand() + " " + request.getName() + " already exists");
		}
		
		Category category = Optional
			.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
			.orElseGet(() -> {
				Category newCategory = new Category(request.getCategory().getName());
				return categoryRepository.save(newCategory);
			});
		
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}
	
	private boolean productExists(String name, String brand) {
		return productRepository.existsByNameAndBrand(name, brand);
	}
	
	private Product createProduct(CreateProductRequest request, Category category) {
		return new Product(
			request.getName(),
			request.getBrand(),
			request.getPrice(),
			request.getInventory(),
			request.getDescription(),
			category
		);
	}
	
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
	}
	
	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
			() -> {
				throw new ProductNotFoundException("Product not found!");
			}
		);
	}
	
	@Override
	public Product updateProduct(UpdateProductRequest request, Long productId) {
		return productRepository.findById(productId)
			.map(existingProduct -> updateExistingProduct(existingProduct, request))
			.map(productRepository::save)
			.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
	}
	
	private Product updateExistingProduct(
		Product existingProduct,
		UpdateProductRequest request
	) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}
	
	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}
	
	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}
	
	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}
	
	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}
	
	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}
	
	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this::convertToDto).toList();
	}
	
	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream()
			.map(image -> modelMapper.map(image, ImageDto.class))
			.toList();
		productDto.setImages(imageDtos);
		return productDto;
	}
}
