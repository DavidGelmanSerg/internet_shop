package ru.gelman.orders_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.orders_service.dto.ProductInfoDto;
import ru.gelman.orders_service.entity.Order;
import ru.gelman.orders_service.entity.Product;
import ru.gelman.orders_service.enums.OrderStatus;
import ru.gelman.orders_service.exception.NotFoundException;
import ru.gelman.orders_service.repository.OrderRepository;
import ru.gelman.orders_service.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Autowired
    public OrderService(OrderRepository repository, ProductRepository productRepository) {
        this.orderRepository = repository;
        this.productRepository = productRepository;
    }

    public Order create(Long clientId, List<ProductInfoDto> productInfos) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setClientId(clientId);

        List<Product> productList = new ArrayList<>();
        for (ProductInfoDto productInfoDto : productInfos) {
            Long productId = productInfoDto.productId();
            Product product = productRepository.findById(productId).orElseThrow(() -> NotFoundException.productNotFound(productId));
            if (product.getAmount() < productInfoDto.productAmount()) {
                throw new IllegalArgumentException(
                        String.format(
                                "Invalid product amount. productId : %d, has: %d, requested: %d",
                                product.getId(),
                                product.getAmount(),
                                productInfoDto.productAmount()
                        )
                );
            }
            productList.add(product);
        }
        order.setProducts(productList);
        order.updateTotalCost();
        log.info("saving new order {}", order);
        return orderRepository.save(order);
    }

    public Order changeStatus(Long id, OrderStatus status) {
        log.info("searching order by id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> NotFoundException.orderNotFound(id));

        log.info("found order {}. changing status to {}", order, status);
        order.setStatus(status);

        log.info("saving order {}", order);
        return orderRepository.save(order);
    }

    public void addProducts(Long id, List<Long> productIds) {
        log.info("searching order by id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> NotFoundException.orderNotFound(id));

        log.info("searching products by ids {}", productIds);
        List<Product> products = productRepository.findAllById(productIds);

        log.info("adding products: {} to order {}", products, order);
        order.addProducts(products);
        order.updateTotalCost();
        orderRepository.save(order);
    }

    public void removeProducts(Long id, List<Long> productIds) {
        log.info("searching order by id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> NotFoundException.orderNotFound(id));

        log.info("searching products by ids {}", productIds);
        List<Product> products = productRepository.findAllById(productIds);

        log.info("removing products: {} from order {}", products, order);
        order.removeProducts(products);
        order.updateTotalCost();
        orderRepository.save(order);
    }

    public List<Order> getOrderListForClient(Long id) {
        log.info("searching orders by client id: {}", id);
        return orderRepository.findByClientId(id);
    }

    public List<Order> getFullOrderList() {
        log.info("extracting full product list");
        return orderRepository.findAll();
    }
}
