package com.onestep.business_management.Controller;

import com.onestep.business_management.Utils.InvoicePDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.print.PrintException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoicePDF invoicePDF;

    @PostMapping("/print/{id}")
    public ResponseEntity<String> printInvoice(@PathVariable("id") Integer orderId) throws PrintException {
        try {
            invoicePDF.printAndDeleteInvoicePDF(orderId);
            return new ResponseEntity<>("Invoice printed and deleted successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to print invoice: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
