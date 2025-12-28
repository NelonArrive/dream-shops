package dev.nelon.dreamshops.service.cart;

import dev.nelon.dreamshops.model.Cart;
import dev.nelon.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
	Cart getCart(Long id);
	
	void clearCart(Long id);
	
	BigDecimal getTotalPrice(Long id);
	
	Cart initializeNewCart(User user);
	
	Cart getCartByUserId(Long userId);
}
