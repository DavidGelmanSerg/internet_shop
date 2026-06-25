package ru.gelman.orders_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.gelman.orders_service.dto.CreateOrderRq;
import ru.gelman.orders_service.dto.KafkaOrderCreatedEvent;
import ru.gelman.orders_service.dto.KafkaOrderStatusUpdatedEvent;
import ru.gelman.orders_service.dto.OrderDto;
import ru.gelman.orders_service.entity.Order;
import ru.gelman.orders_service.enums.OrderStatus;
import ru.gelman.orders_service.mapper.OrderMapper;
import ru.gelman.orders_service.service.OrderService;

import java.util.List;

@RestController
@Slf4j
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final KafkaTemplate<String, KafkaOrderCreatedEvent> kafkaOrderCreatedEventTemplate;
    private final KafkaTemplate<String, KafkaOrderStatusUpdatedEvent> KafkaOrderStatusUpdatedEventTemplate;

    @Value("${app.kafka.orders.topic}")
    private String kafkaOrderEventsTopic;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper, KafkaTemplate<String, KafkaOrderCreatedEvent> kafkaTemplate, KafkaTemplate<String, KafkaOrderCreatedEvent> kafkaOrderCreatedEventTemplate, KafkaTemplate<String, KafkaOrderStatusUpdatedEvent> kafkaOrderStatusUpdatedEventTemplate) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.kafkaOrderCreatedEventTemplate = kafkaOrderCreatedEventTemplate;
        KafkaOrderStatusUpdatedEventTemplate = kafkaOrderStatusUpdatedEventTemplate;
    }

    @PostMapping("/orders")
    public OrderDto createOrder(@RequestBody CreateOrderRq rq) {
        log.info("creating order by {}: {}", rq.clientId(), rq);
        Order created = orderService.create(rq.clientId(), rq.productInfos());
        kafkaOrderCreatedEventTemplate.send(kafkaOrderEventsTopic, new KafkaOrderCreatedEvent(created.getId(), created.getClientId()));
        log.info("created order: {}", created);
        return orderMapper.toOrderDto(created);
    }

    @GetMapping("/orders/{id}")
    public List<OrderDto> getOrderList(@PathVariable Long id) {
        log.info("getting order list for client {}", id);
        List<Order> orderList = orderService.getOrderListForClient(id);
        log.info("found orders: {} for client: {}", orderList, id);
        return orderMapper.toOrderDtoList(orderList);
    }

    @GetMapping("/orders")
    public List<OrderDto> getFullOrderList() {
        log.info("getting full order list for client");
        List<Order> orderList = orderService.getFullOrderList();
        log.info("found orders: {} for client", orderList);
        return orderMapper.toOrderDtoList(orderList);
    }

    @PatchMapping("/orders/{id}/status")
    public OrderDto updateOrderStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        log.info("cancelling order with id {}", id);
        Order updated = orderService.changeStatus(id, status);
        KafkaOrderStatusUpdatedEventTemplate.send(kafkaOrderEventsTopic, new KafkaOrderStatusUpdatedEvent(updated.getId(), updated.getClientId(), updated.getStatus().toString()));
        log.info("updated order: {}", updated);
        return orderMapper.toOrderDto(updated);
    }
}
