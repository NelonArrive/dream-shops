package dev.nelon.dreamshops.service.cart;

import dev.nelon.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
	Cart getCart(Long id);
	
	void clearCart(Long id);
	
	BigDecimal getTotalPrice(Long id);
	
	Long initializeNewCart();
	
	Cart getCartByUserId(Long userId);
}
