package dev.nelon.dreamshops.service.order;

import dev.nelon.dreamshops.dto.OrderDto;
import dev.nelon.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
	Order placeOrder(Long userId);
	
	OrderDto getOrder(Long orderId);
	
	List<OrderDto> getUserOrders(Long userId);
	
	OrderDto convertToDto(Order order);
}
