package com.shop.ordersystem.controller;

import com.shop.ordersystem.model.Order;
import com.shop.ordersystem.repository.CustomerRepository;
import com.shop.ordersystem.repository.OrderItemRepository;
import com.shop.ordersystem.repository.OrderRepository;
import com.shop.ordersystem.repository.ProductRepository;
import com.shop.ordersystem.specification.OrderSpecification;

import jakarta.servlet.http.HttpServletResponse;

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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderRepository     orderRepository;
    @Autowired private CustomerRepository  customerRepository;
    @Autowired private ProductRepository   productRepository;
    @Autowired private OrderItemRepository orderItemRepository;

    /* ------------------------------------------------- list --------------------------------------------------- */

    @GetMapping
    public String listOrders(
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "status",       required = false) String status,
            @RequestParam(value = "dateFrom",     required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "dateTo",       required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Model model) {

        Specification<Order> spec = OrderSpecification.filter(customerName, status, dateFrom, dateTo);
        List<Order> orders       = orderRepository.findAll(spec);

        model.addAttribute("orders",       orders);
        model.addAttribute("customerName", customerName);
        model.addAttribute("status",       status);
        model.addAttribute("dateFrom",     dateFrom);
        model.addAttribute("dateTo",       dateTo);
        return "order-list";
    }

    /* ------------------------------------------------- CRUD --------------------------------------------------- */

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("order",     new Order());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products",  productRepository.findAll());
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(
            @RequestParam("customerId") Long customerId,
            @RequestParam("status")     String status,
            @RequestParam(value = "productIds", required = false) Long[]    productIds,
            @RequestParam(value = "quantities", required = false) Integer[] quantities) {

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

    /* --------------------------------------------- EXPORT ‑ EXCEL -------------------------------------------- */

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        List<Order> orders = orderRepository.findAll();

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Orders");
            String[] cols = {"ID","Customer","Date","Status","Total"};

            /* --- header --- */
            Row header = sheet.createRow(0);
            CellStyle hStyle = wb.createCellStyle();

            /* ► POI‑Font  */
            org.apache.poi.ss.usermodel.Font hFont = wb.createFont();
            hFont.setBold(true);
            hStyle.setFont(hFont);

            for (int i = 0; i < cols.length; i++) {
                Cell c = header.createCell(i);
                c.setCellValue(cols[i]);
                c.setCellStyle(hStyle);
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            int rowIdx = 1;

            for (Order o : orders) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(o.getId());
                row.createCell(1).setCellValue(o.getCustomer().getName());
                row.createCell(2).setCellValue(o.getOrderDate().format(dtf));
                row.createCell(3).setCellValue(o.getStatus());
                row.createCell(4).setCellValue(o.getTotal().doubleValue());   // BigDecimal → double
            }

            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }
            wb.write(response.getOutputStream());
        }
    }

    /* ---------------------------------------------- EXPORT ‑ PDF --------------------------------------------- */

    @GetMapping("/report/pdf")
    public void exportToPDF(HttpServletResponse response)
            throws DocumentException, IOException {

        List<Order> orders = orderRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=orders.pdf");

        Document doc = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        /* title */
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Orders Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);

        /* generation date */
        Paragraph genDate = new Paragraph(
                "Generated: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        genDate.setAlignment(Element.ALIGN_RIGHT);
        doc.add(genDate);
        doc.add(Chunk.NEWLINE);

        /* table */
        PdfPTable tbl = new PdfPTable(5);
        tbl.setWidthPercentage(100);
        tbl.setWidths(new float[]{1f,3f,3f,2f,2f});

        com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String[] headers = {"ID","Customer","Date","Status","Total"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbl.addCell(cell);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Order o : orders) {
            tbl.addCell(String.valueOf(o.getId()));
            tbl.addCell(o.getCustomer().getName());
            tbl.addCell(o.getOrderDate().format(dtf));
            tbl.addCell(o.getStatus());
            tbl.addCell(
                    o.getTotal()
                            .setScale(2, RoundingMode.HALF_UP)
                            .toPlainString());
        }

        doc.add(tbl);
        doc.close();
    }
}
