package dev.nelon.dreamshops.controller;

import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.Cart;
import dev.nelon.dreamshops.response.ApiResponse;
import dev.nelon.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
	private final ICartService cartService;
	
	@GetMapping("/{cartId}/my-cart")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
		try {
			Cart cart = cartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("success", cart));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
		cartI
	}
}
