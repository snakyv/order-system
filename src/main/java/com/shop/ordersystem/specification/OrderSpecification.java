package com.shop.ordersystem.specification;

import com.shop.ordersystem.model.Order;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> filter(
            String customerName,
            String status,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    ) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(customerName)) {
                // JOIN customer и LIKE по имени
                Join<?, ?> cust = root.join("customer", JoinType.LEFT);
                predicates.add(cb.like(
                        cb.lower(cust.get("name")),
                        "%" + customerName.toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(status)) {
                predicates.add(cb.equal(
                        cb.lower(root.get("status")),
                        status.toLowerCase()
                ));
            }

            if (dateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("orderDate"), dateFrom));
            }
            if (dateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("orderDate"), dateTo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
