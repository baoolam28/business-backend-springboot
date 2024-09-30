package com.onestep.business_management.Utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Repository.OrderOfflineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.*;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class InvoicePDF {

    @Autowired
    private OrderOfflineRepository orderRepository;

    public void printAndDeleteInvoicePDF(Integer orderId) throws IOException, PrintException {
        OrderOffline order = getOrder(orderId);
        if (order == null) {
            throw new IOException("Order not found");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A5);

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add content to PDF
            document.add(new Paragraph("Invoice ID: " + order.getOrderId()));
            document.add(new Paragraph("Customer: " + order.getCustomer().getName()));
            document.add(new Paragraph("Order Date: " + order.getOrderDate()));

            // Add more invoice details here

        } catch (Exception e) {
            throw new IOException("Error creating PDF", e);
        } finally {
            document.close();
        }

        // Save the PDF to a temporary file
        File tempFile = File.createTempFile("invoice_" + orderId, ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(outputStream.toByteArray());
        }

        // Print the PDF
        printFile(tempFile);

        // Delete the temporary file
        if (!tempFile.delete()) {
            throw new IOException("Failed to delete the temporary file");
        }
    }

    private void printFile(File file) throws IOException, PrintException {
        // Get available PrintServices
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE,
                null);
        if (printServices.length == 0) {
            throw new IOException("No print service available");
        }

        // Use the first available PrintService or let the user select
        PrintService printService = printServices[0];
        DocPrintJob printJob = printService.createPrintJob();
        Doc doc = new SimpleDoc(new ByteArrayInputStream(Files.readAllBytes(file.toPath())),
                DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        printJob.print(doc, null);
    }

    private String formatPrice(double price) {
        return String.format("%,.0f VND", price);
    }

    private OrderOffline getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
