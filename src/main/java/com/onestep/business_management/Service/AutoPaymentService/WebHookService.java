package com.onestep.business_management.Service.AutoPaymentService;

import com.onestep.business_management.DTO.qrCodeDTO.qrCodeRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class WebHookService {
    private final String webhookUrl = "http://localhost:3000/api/webhook/web-hook"; // URL của endpoint trên frontend

    public void sendWebhook(UUID message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo payload JSON
        String payload = "{\"message\": \"" + message + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                webhookUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Webhook sent successfully!");
        } else {
            System.out.println("Failed to send webhook: " + responseEntity.getStatusCode());
        }
    }
}
