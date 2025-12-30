package dev.nelon.dreamshops.controller;

import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.Cart;
import dev.nelon.dreamshops.model.User;
import dev.nelon.dreamshops.response.ApiResponse;
import dev.nelon.dreamshops.service.cart.ICartItemService;
import dev.nelon.dreamshops.service.cart.ICartService;
import dev.nelon.dreamshops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
	private final ICartItemService cartItemService;
	private final ICartService cartService;
	private final IUserService userService;
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(
		@RequestParam Long productId,
		@RequestParam Integer quantity
	) {
		try {
			User user = userService.getAuthenticatedUser();
			Cart cart = cartService.initializeNewCart(user);
			
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		} catch (JwtException e){
			return  ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/cart/{cartId}/item/{itemId}")
	public ResponseEntity<ApiResponse> removeItemFromCart(
		@PathVariable Long cartId,
		@PathVariable Long itemId
	) {
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/cart/{cartId}/item/{itemId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(
		@PathVariable Long cartId,
		@PathVariable Long itemId,
		@RequestParam Integer quantity
	) {
		try {
			cartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}