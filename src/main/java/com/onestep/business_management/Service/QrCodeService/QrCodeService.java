package com.onestep.business_management.Service.QrCodeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onestep.business_management.DTO.qrCodeDTO.qrCodeRequest;

@Service
public class QrCodeService {
    
    private final RestTemplate restTemplate;

    @Value("${vietqr.clientId}")
    private String clientId;

    @Value("${vietqr.apiKey}")
    private String apiKey;

    public QrCodeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createQRCode(qrCodeRequest qrCodeRequest) {
        String url = "https://api.vietqr.io/v2/generate";
        System.out.println("tranfer request: " + qrCodeRequest.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id", clientId);
        headers.set("x-api-key", apiKey);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<qrCodeRequest> requestEntity = new HttpEntity<>(qrCodeRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);

        

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody(); 
        } else {
            return "Error: " + responseEntity.getStatusCode() + "message: " + responseEntity.getBody(); 
        }
    }
}
