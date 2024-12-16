package com.onestep.business_management.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.onestep.business_management.DTO.OrderDTO.OrderDetailResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderResponse;
import com.onestep.business_management.DTO.qrCodeDTO.qrCodeRequest;
import com.onestep.business_management.Service.OrderService.OrderService;
import com.onestep.business_management.Service.QrCodeService.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class InvoicePDF {

    @Autowired
    private OrderService orderService;

    @Autowired
    private QrCodeService qrCodeService;

    public void printOrder(UUID orderId) throws Exception {
        OrderResponse order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new Exception("Order not found with ID: " + orderId);
        }

        // File path for saving the PDF
        String filePath = "Invoice_" + orderId + ".pdf";
        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        BaseFont baseFont = BaseFont.createFont("fonts/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12);

        PdfPTable detailTable = new PdfPTable(4);
        detailTable.setWidthPercentage(100);
        detailTable.setSpacingBefore(10);
        detailTable.setSpacingAfter(10);
        // Add Order Details Table
        addTableHeader(detailTable, "Tên Sản Phẩm", "Mã Vạch", "Số Lượng", "Thành Tiền", font);
        int totalAmount = 0;
        for (OrderDetailResponse detail : order.getOrderDetails()) {
            String productName = detail.getName() != null ? detail.getName() : "";
            detailTable.addCell(createCell(productName, font));
            detailTable.addCell(createCell(detail.getBarcode(), font));
            detailTable.addCell(createCell(String.valueOf(detail.getQuantity()), font));

            double totalPrice = detail.getQuantity() * detail.getPrice();
            totalAmount += totalPrice;
            detailTable.addCell(createCell(formatCurrency(totalPrice), font));
        }

        // Use CompletableFuture to handle logo and QR code generation asynchronously
        CompletableFuture<Image> logoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return Image.getInstance(new URL(order.getLogoStore()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        int finalTotalAmount = totalAmount;
        CompletableFuture<String> qrCodeFuture = CompletableFuture.supplyAsync(() -> {
            qrCodeRequest qrcodeRq = new qrCodeRequest();
            qrcodeRq.setAccountName("Dương Bảo Lâm");
            qrcodeRq.setAmount(String.valueOf(finalTotalAmount));
            qrcodeRq.setAccountNo("5279771");
            qrcodeRq.setAcqId("970416");
            qrcodeRq.setTemplate("NLEMX5j");
            qrcodeRq.setAddInfo(String.valueOf(order.getOrderId()));
            return qrCodeService.createQRCode(qrcodeRq);
        });

        // Add Logo
        Image logo = logoFuture.get();
        if (logo != null) {
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleAbsolute(100, 100);
            document.add(logo);
        }

        // Add space and Title
        document.add(new Paragraph(" "));
        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add Metadata
        document.add(new Paragraph(" "));
        PdfPTable metadataTable = new PdfPTable(2);
        metadataTable.setWidthPercentage(100);
        metadataTable.setSpacingBefore(10);
        metadataTable.setSpacingAfter(10);

        addTableRow(metadataTable, "Mã Hóa Đơn:", orderId.toString(), font);
        addTableRow(metadataTable, "Ngày mua:", getCurrentDate(), font);
        addTableRow(metadataTable, "Ngày Đặt:", formatDate(order.getOrderDate()), font);
        addTableRow(metadataTable, "Khách Hàng:", order.getCustomerName() != null ? order.getCustomerName() : "Khách lẻ", font);
        addTableRow(metadataTable, "Cửa Hàng:", order.getStoreName() != null ? order.getStoreName() : "N/A", font);
        document.add(metadataTable);


        document.add(detailTable);

        // Add Total Amount
        Font totalFont = new Font(baseFont, 14, Font.BOLD);
        Paragraph totalParagraph = new Paragraph("Tổng Tiền: " + formatCurrency(totalAmount), totalFont);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);

        // Handle QR Code Image
        String qrCodeUrl = qrCodeFuture.get();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(qrCodeUrl);

        // Extract qrDataURL from the JSON response
        String qrDataUrl = rootNode.path("data").path("qrDataURL").asText();

        if (qrDataUrl != null && qrDataUrl.startsWith("data:image/png;base64,")) {
            String base64Image = qrDataUrl.split(",")[1];
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            Image qrCodeImage = Image.getInstance(imageBytes);

            qrCodeImage.setAlignment(Element.ALIGN_RIGHT);
            qrCodeImage.scaleAbsolute(100, 100); // Adjust size if needed
            document.add(qrCodeImage);
        } else {
            System.out.println("QR Code URL is invalid.");
        }

        // Close the document
        document.close();

        // Open the PDF
        openFile(filePath);
        System.out.println("Invoice generated successfully: " + filePath);
    }

    private void addTableRow(PdfPTable table, String header, String value, Font font) {
        table.addCell(createCell(header, Element.ALIGN_LEFT, font));
        table.addCell(createCell(value, Element.ALIGN_RIGHT, font));
    }

    private void addTableHeader(PdfPTable table, String header1, String header2, String header3, String header4, Font font) {
        table.addCell(createCell(header1, Element.ALIGN_CENTER, font));
        table.addCell(createCell(header2, Element.ALIGN_CENTER, font));
        table.addCell(createCell(header3, Element.ALIGN_CENTER, font));
        table.addCell(createCell(header4, Element.ALIGN_CENTER, font));
    }

    private PdfPCell createCell(String content, Font font) {
        return createCell(content, Element.ALIGN_CENTER, font);
    }

    private PdfPCell createCell(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    private String getCurrentDate() {
        return formatDate(new Date());
    }

    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#,##0 VND");
        return df.format(amount);
    }

    public static void openFile(String filePath) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "start", filePath);
            } else if (os.contains("mac")) {
                processBuilder = new ProcessBuilder("open", filePath);
            } else if (os.contains("nix") || os.contains("nux")) {
                processBuilder = new ProcessBuilder("xdg-open", filePath);
            } else {
                throw new UnsupportedOperationException("Unsupported OS");
            }
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }
}
