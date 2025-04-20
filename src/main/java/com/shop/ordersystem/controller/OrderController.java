package com.shop.ordersystem.controller;

import com.shop.ordersystem.model.Order;
import com.shop.ordersystem.repository.CustomerRepository;
import com.shop.ordersystem.repository.OrderItemRepository;
import com.shop.ordersystem.repository.OrderRepository;
import com.shop.ordersystem.repository.ProductRepository;
import com.shop.ordersystem.specification.OrderSpecification;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;                // iText Font
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderRepository        orderRepository;
    @Autowired private CustomerRepository     customerRepository;
    @Autowired private ProductRepository      productRepository;
    @Autowired private OrderItemRepository    orderItemRepository;

    @GetMapping
    public String listOrders(
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "status",       required = false) String status,
            @RequestParam(value = "dateFrom",     required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "dateTo",       required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Model model
    ) {
        Specification<Order> spec =
                OrderSpecification.filter(customerName, status, dateFrom, dateTo);
        List<Order> orders = orderRepository.findAll(spec);

        model.addAttribute("orders",       orders);
        model.addAttribute("customerName", customerName);
        model.addAttribute("status",       status);
        model.addAttribute("dateFrom",     dateFrom);
        model.addAttribute("dateTo",       dateTo);
        return "order-list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("order",     new Order());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products",  productRepository.findAll());
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(
            @RequestParam("customerId") Long   customerId,
            @RequestParam("status")     String status,
            @RequestParam(value = "productIds", required = false) Long[]   productIds,
            @RequestParam(value = "quantities",  required = false) Integer[] quantities
    ) {
        var customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return "redirect:/orders?error=customerNotFound";
        }

        var order = new Order();
        order.setCustomer(customer);
        order.setStatus(status);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        if (productIds != null && quantities != null) {
            for (int i = 0; i < productIds.length; i++) {
                var product = productRepository.findById(productIds[i]).orElse(null);
                if (product != null && quantities[i] > 0) {
                    var item = new com.shop.ordersystem.model.OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(quantities[i]);
                    order.getOrderItems().add(item);
                    orderItemRepository.save(item);
                }
            }
        }

        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        model.addAttribute("order",     order);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products",  productRepository.findAll());
        return "order-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    /**
     * Экспорт текущего списка заказов в Excel.
     */
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Order> orders = orderRepository.findAll();

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=orders.xlsx"
        );

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Orders");

            // Шапка
            Row header = sheet.createRow(0);
            String[] cols = {"ID","Customer","Date","Status","Total"};
            CellStyle hStyle = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font excelFont = wb.createFont();
            excelFont.setBold(true);
            hStyle.setFont(excelFont);

            for (int i = 0; i < cols.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(cols[i]);
                cell.setCellStyle(hStyle);
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            int rowIdx = 1;
            for (Order o : orders) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(o.getId());
                row.createCell(1).setCellValue(o.getCustomer().getName());
                row.createCell(2).setCellValue(o.getOrderDate().format(dtf));
                row.createCell(3).setCellValue(o.getStatus());
                row.createCell(4).setCellValue(o.getTotal());
            }

            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(response.getOutputStream());
        }
    }

    /**
     * Экспорт текущего списка заказов в PDF.
     */
    @GetMapping("/report/pdf")
    public void exportToPDF(HttpServletResponse response)
            throws DocumentException, IOException {
        List<Order> orders = orderRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=orders.pdf"
        );

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Заголовок
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Orders Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Дата генерации
        Paragraph genDate = new Paragraph(
                "Generated: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        );
        genDate.setAlignment(Element.ALIGN_RIGHT);
        document.add(genDate);
        document.add(Chunk.NEWLINE);

        // Таблица
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f,3f,3f,2f,2f});

        // Шапка PDF
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String[] headers = {"ID","Customer","Date","Status","Total"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(h, headFont));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Order o : orders) {
            table.addCell(String.valueOf(o.getId()));
            table.addCell(o.getCustomer().getName());
            table.addCell(o.getOrderDate().format(dtf2));
            table.addCell(o.getStatus());
            table.addCell(String.format("%.2f", o.getTotal()));
        }

        document.add(table);
        document.close();
    }
}
