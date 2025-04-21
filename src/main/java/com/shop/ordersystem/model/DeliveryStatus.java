package com.shop.ordersystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatus {

    /* id таблицы = id заказа */
    @Id
    private Long id;    // Hibernate сам возьмёт тип колонки из PK orders

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId                                    // ← связываем PK = FK
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Order order;

    @Column(length = 50)
    private String status;
}
