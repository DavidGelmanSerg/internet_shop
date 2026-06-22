package ru.gelman.orders_service.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.gelman.orders_service.entity.Product;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
