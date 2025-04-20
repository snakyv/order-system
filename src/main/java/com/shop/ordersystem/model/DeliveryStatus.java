package com.shop.ordersystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatus {

    @Id
    @Column(name = "order_id", columnDefinition = "BIGINT")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Order order;

    @Column(length = 50)
    private String status;
}
