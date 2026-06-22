package ru.gelman.orders_service.entity;

import jakarta.persistence.*;
import lombok.Data;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private List<Product> products = new ArrayList<>();

    public void addProducts(List<Product> productList) {
        this.products.addAll(productList);
    }

    public void removeProducts(List<Product> productList) {
        productList.forEach(this.products::remove);
    }

    public void updateTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Product product : this.products) {
            totalCost = totalCost.add(product.getCost());
        }
        this.totalCost = totalCost;
    }
}
