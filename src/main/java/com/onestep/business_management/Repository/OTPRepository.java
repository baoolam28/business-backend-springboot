package com.onestep.business_management.Repository;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

@Repository
public class OTPRepository {
    private static final String API_URL = "https://api.stringee.com/v1/call2/callout";
    private static final String API_SID = "SK.0.2PXcIoW3vzX7C4kcS04VvNV3NCbRzWi"; 
    private static final String API_SECRET = "RE8zSXRSQUNYOHdEUlhWQVpKazgwTEJGZkNOempxU24=";
    private static final String FROM_PHONE_NUMBER = "842473001690";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(API_SECRET.getBytes());
    }

    private String generateJwtToken() {
        // Tạo JWT Token sử dụng jjwt
        return Jwts.builder()
                .setIssuer(API_SID) // Đặt API SID làm issuer
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // Thời gian hết hạn token (1 giờ)
                .claim("rest_api", true) // Đặt trường rest_api thành true
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // Ký bằng API Secret Key
                .compact();
    }

    public boolean callOtp(String phoneNumber, String otpCode) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = createJsonPayload(phoneNumber, otpCode);
       

        String jwtToken = generateJwtToken();

        RequestBody body = RequestBody.create(
            json.toString(),
            MediaType.get("application/json; charset=utf-8")
        );
        
        System.out.println("Payload JSON: " + json.toString(4));

        Request request = new Request.Builder()
            .url(API_URL)
            .post(body)
            .addHeader("X-STRINGEE-AUTH", jwtToken)
            .addHeader("Content-Type", "application/json")
            .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Response Body: " + responseBody);
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JSONObject createJsonPayload(String phoneNumber, String otpCode) {
        JSONObject json = new JSONObject();
        json.put("from", new JSONObject()
                .put("type", "internal")
                .put("number", FROM_PHONE_NUMBER)
                .put("alias", FROM_PHONE_NUMBER));


        JSONArray tos = new JSONArray();
        JSONObject to = new JSONObject();       
        to.put("type", "external");
        to.put("number", phoneNumber);
        to.put("alias", phoneNumber);
        tos.put(to);
        json.put("to", tos);


        JSONArray actions = new JSONArray();

        for(int i = 0; i < 3; i++){
            JSONObject action = new JSONObject();
            action.put("action", "talk");
            action.put("text", "Mã OPT của bạn là: " + otpCode.replaceAll("", " ").trim());
            action.put("voice", "female");
            action.put("speed", -1);
            action.put("bargeIn", true);
            action.put("loop", 1);
            actions.put(action);
        }
       
        json.put("actions", actions);

        return json;
    }
    
}
