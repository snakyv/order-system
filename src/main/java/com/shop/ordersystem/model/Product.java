package com.shop.ordersystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "products")           // ← реальное имя таблицы в БД
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal price;       // BigDecimal → точные деньги

    @Column(nullable = false)
    private Integer quantity;
}
