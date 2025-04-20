package com.shop.ordersystem.repository;

import com.shop.ordersystem.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
}
