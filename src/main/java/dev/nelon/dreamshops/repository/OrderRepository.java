package dev.nelon.dreamshops.repository;

import dev.nelon.dreamshops.model.Order;
import dev.nelon.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);
	
	Long user(User user);
}
