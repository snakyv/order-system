package com.shop.ordersystem.controller;

import com.shop.ordersystem.model.DeliveryStatus;
import com.shop.ordersystem.model.Order;
import com.shop.ordersystem.repository.DeliveryStatusRepository;
import com.shop.ordersystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delivery-status")
public class DeliveryStatusController {

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Показываем список всех статусов
    @GetMapping
    public String listStatuses(Model model) {
        model.addAttribute("statuses", deliveryStatusRepository.findAll());
        return "delivery-status-list";
    }

    // GET /delivery-status/new — форма создания нового статуса
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "delivery-status-form";
    }

    // GET /delivery-status/edit/{id} — форма редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DeliveryStatus status = deliveryStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статус не знайдений: " + id));
        model.addAttribute("status", status);
        model.addAttribute("orders", orderRepository.findAll());
        return "delivery-status-form";
    }

    // Обработчик и для новых, и для правок
    @PostMapping("/save")
    public String save(
            @RequestParam("orderId") Long orderId,
            @RequestParam("status") String statusText,
            @RequestParam(value = "id", required = false) Long id  // скрытое поле формы
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Замовлення не знайдене: " + orderId));

        DeliveryStatus ds = (id != null)
                ? deliveryStatusRepository.findById(id).orElse(new DeliveryStatus())
                : new DeliveryStatus();

        ds.setOrder(order);
        ds.setStatus(statusText);
        deliveryStatusRepository.save(ds);

        return "redirect:/delivery-status";
    }
}
