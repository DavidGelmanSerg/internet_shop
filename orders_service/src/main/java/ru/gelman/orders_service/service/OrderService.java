package ru.gelman.orders_service.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.orders_service.dto.ProductInfoDto;
import ru.gelman.orders_service.entity.Order;
import ru.gelman.orders_service.entity.OrderProduct;
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

    @Transactional
    public Order create(Long clientId, List<ProductInfoDto> productInfoDtos) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setClientId(clientId);

        order = orderRepository.save(order);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (ProductInfoDto productInfo : productInfoDtos) {
            Product product = productRepository
                    .findById(productInfo.productId())
                    .orElseThrow(() -> NotFoundException.productNotFound(productInfo.productId()));
            if (product.getAmount() < productInfo.productAmount()) {
                throw new IllegalArgumentException(String.format("invalid amount for product %s. requested %d", product, productInfo.productAmount()));
            }
            int amount = product.getAmount() - productInfo.productAmount();
            product.setAmount(amount);
            productRepository.save(product);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setOrderId(order.getId());
            orderProduct.setProduct(product);
            orderProduct.setProductId(product.getId());
            orderProduct.setAmount(productInfo.productAmount());
            orderProductList.add(orderProduct);
        }
        order.setOrderProducts(orderProductList);
        order.updateTotalCost();
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

    public List<Order> getOrderListForClient(Long id) {
        log.info("searching orders by client id: {}", id);
        return orderRepository.findByClientId(id);
    }

    public List<Order> getFullOrderList() {
        log.info("extracting full product list");
        return orderRepository.findAll();
    }
}