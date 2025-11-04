package nelon.arrive.dreamshop.service.product;

import nelon.arrive.dreamshop.model.Product;
import nelon.arrive.dreamshop.request.AddProductRequest;
import nelon.arrive.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest product, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);
}
