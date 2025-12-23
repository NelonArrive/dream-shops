package dev.nelon.dreamshops.repository;

import dev.nelon.dreamshops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
