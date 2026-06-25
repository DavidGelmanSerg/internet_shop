package ru.gelman.orders_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.gelman.orders_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private BigDecimal totalCost;
    private Long clientId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addProducts(List<OrderProduct> productList) {
        this.orderProducts.addAll(productList);
    }

    public void removeProducts(List<OrderProduct> productList) {
        productList.forEach(this.orderProducts::remove);
    }

    public void updateTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderProduct orderProduct : this.orderProducts) {
            totalCost = totalCost.add(orderProduct.getProduct().getCost().multiply(BigDecimal.valueOf(orderProduct.getAmount())));
        }
        this.totalCost = totalCost;
    }

    public final List<Product> getProducts() {
        return orderProducts.stream().map(OrderProduct::getProduct).toList();
    }
}
