package dev.nelon.dreamshops.repository;

import dev.nelon.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	void deleteAllByCartId(Long cartId);
}
