package dev.nelon.dreamshops.service.order;

import dev.nelon.dreamshops.exception.ResourceNotFoundException;
import dev.nelon.dreamshops.model.Order;
import dev.nelon.dreamshops.model.OrderItem;
import dev.nelon.dreamshops.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderRepository orderRepository;
	
	@Override
	public Order placeOrder(Long userId) {
		return null;
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
}
