package ru.gelman.orders_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String name;
    private Long size;
    private String contentType;

    @Column(name = "is_preview")
    private boolean preview;

    @Column(columnDefinition = "BYTEA")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
}
