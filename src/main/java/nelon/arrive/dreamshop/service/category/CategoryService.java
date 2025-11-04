package nelon.arrive.dreamshop.service.category;

import lombok.RequiredArgsConstructor;
import nelon.arrive.dreamshop.exception.AlreadyExistException;
import nelon.arrive.dreamshop.exception.ResourceNotFoundException;
import nelon.arrive.dreamshop.model.Category;
import nelon.arrive.dreamshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	private final CategoryRepository categoryRepository;
	
	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}
	
	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}
	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	@Override
	public Category addCategory(Category category) {
		return Optional.of(category)
			.filter(c -> !categoryRepository.existByName(c.getName()))
			.map(categoryRepository::save)
			.orElseThrow(() -> new AlreadyExistException(category.getName() +
				"already exists"));
	}
	
	@Override
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}
	
	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id)
			.ifPresentOrElse(categoryRepository::delete, () -> {
				throw new ResourceNotFoundException("Category not found!");
			});
	}
}
