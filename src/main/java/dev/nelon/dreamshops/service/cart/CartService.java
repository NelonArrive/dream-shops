package dev.nelon.dreamshops.service.cart;

import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.Cart;
import dev.nelon.dreamshops.repository.CartItemRepository;
import dev.nelon.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	
	@Override
	public Cart getCart(Long id) {
		return cartRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
	}
	
	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cart.updateTotalAmount();
		cartRepository.save(cart);
	}
	
	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}
	
	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		return cartRepository.save(newCart).getId();
	}
	
	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId);
	}
}
