package com.onestep.business_management.Controller.SellerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onestep.business_management.DTO.qrCodeDTO.qrCodeRequest;
import com.onestep.business_management.Service.QrCodeService.QrCodeService;

@RestController
@RequestMapping("/api/seller/qr")
public class QrController {
    
    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("/create")
    public ResponseEntity<String> createQrCode(@RequestBody qrCodeRequest qrCodeRequest) {
        String result = qrCodeService.createQRCode(qrCodeRequest);

        return ResponseEntity.ok(result);
    }
}
