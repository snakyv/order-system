package com.shop.ordersystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // чтобы тип совпадал с orders.id (BIGINT)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Order order;

    // а здесь тоже лучше явно BIGINT
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", columnDefinition = "BIGINT", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
