package ru.gelman.orders_service.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.orders_service.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends ListCrudRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
}
