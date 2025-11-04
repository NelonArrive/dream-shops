package nelon.arrive.dreamshop.repository;

import nelon.arrive.dreamshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findByCategoryName(String category);
	List<Product> findByBrand(String brand);
	List<Product> findByCategoryNameAndBrand(String category, String brand);
	List<Product> findByName(String name);
	List<Product> findByBrandAndName(String brand, String name);
	Long countByBrandAndName(String brand, String name);
}
