package ru.gelman.orders_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders_products")
@IdClass(OrderProductId.class)
@Data
public class OrderProduct {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_amount")
    private Integer amount;
}
