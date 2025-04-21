package com.shop.ordersystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** когда оформлен заказ */
    @Column(name = "order_date", nullable = false)
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    /** кому принадлежит заказ */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Customer customer;

    /** произвольный статус (черновик, оплачен и т.д.) */
    @Column(length = 50)
    private String status;

    /** позиции заказа */
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    /** связь «один‑к‑одному» с таблицей delivery_status */
    @OneToOne(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private DeliveryStatus deliveryStatus;

    /**
     * Полная сумма заказа: Σ(цена × кол-во).
     * Используем BigDecimal для валютных расчётов.
     */
    public BigDecimal getTotal() {
        return orderItems.stream()
                .map(OrderItem::getSubtotal)         // BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
