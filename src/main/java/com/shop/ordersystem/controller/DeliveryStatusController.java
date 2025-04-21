package com.shop.ordersystem.controller;

import com.shop.ordersystem.model.DeliveryStatus;
import com.shop.ordersystem.model.Order;
import com.shop.ordersystem.repository.DeliveryStatusRepository;
import com.shop.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delivery-status")
@RequiredArgsConstructor
public class DeliveryStatusController {

    private final DeliveryStatusRepository deliveryStatusRepository;
    private final OrderRepository          orderRepository;

    /** список всех статусов */
    @GetMapping
    public String listStatuses(Model model) {
        model.addAttribute("statuses", deliveryStatusRepository.findAll());
        return "delivery-status-list";
    }

    /** форма создания */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("status",  new DeliveryStatus());
        model.addAttribute("orders",  orderRepository.findAll());
        return "delivery-status-form";
    }

    /** форма редактирования */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var status = deliveryStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статус не знайдений: " + id));
        model.addAttribute("status",  status);
        model.addAttribute("orders",  orderRepository.findAll());
        return "delivery-status-form";
    }

    /** сохранение (и новое, и редактирование) */
    @PostMapping("/save")
    public String save(
            @RequestParam Long orderId,
            @RequestParam String status,
            @RequestParam(required = false) Long id) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Замовлення не знайдене: " + orderId));

        DeliveryStatus ds = (id != null)
                ? deliveryStatusRepository.findById(id).orElse(new DeliveryStatus())
                : new DeliveryStatus();

        ds.setOrder(order);
        ds.setStatus(status);

        deliveryStatusRepository.save(ds);
        return "redirect:/delivery-status";
    }
}
