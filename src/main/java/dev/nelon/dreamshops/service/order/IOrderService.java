package dev.nelon.dreamshops.service.order;

import dev.nelon.dreamshops.model.Order;

public interface IOrderService {
	Order placeOrder(Long userId);
	
	Order getOrder(Long orderId);
}
