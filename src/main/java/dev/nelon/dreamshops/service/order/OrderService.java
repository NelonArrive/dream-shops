package dev.nelon.dreamshops.service.order;

import dev.nelon.dreamshops.enums.OrderStatus;
import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.Cart;
import dev.nelon.dreamshops.model.Order;
import dev.nelon.dreamshops.model.OrderItem;
import dev.nelon.dreamshops.model.Product;
import dev.nelon.dreamshops.repository.OrderRepository;
import dev.nelon.dreamshops.repository.ProductRepository;
import dev.nelon.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;
	
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		
		Order order = createOrder(cart);
		List<OrderItem> orderItems = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItems));
		order.setTotalAmount(calculateTotalAmount(orderItems));
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(cart.getId());
		
		return savedOrder;
	}
	
	private Order createOrder(Cart cart) {
		Order order= new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	private List<OrderItem> createOrderItems(Order order, Cart cart) {
		return cart.getItems().stream()
			.map(cartItem -> {
				Product product = cartItem.getProduct();
				product.setInventory(product.getInventory() - cartItem.getQuantity());
				productRepository.save(product);
				return new OrderItem(
					order,
					product,
					cartItem.getQuantity(),
					cartItem.getUnitPrice());
			}).toList();
	}
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
		return orderItems
			.stream()
			.map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	@Override
	public Order getOrder(Long orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}
	
	@Override
	public List<Order> getUserOrders(Long userId) {
		return orderRepository.findByUserId(userId);
	}
}
